package aminetti.adventofcode2023.day18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aminetti.adventofcode2023.day18.Day18.Direction.D;
import static aminetti.adventofcode2023.day18.Day18.Direction.U;
import static java.lang.Integer.parseInt;
import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;

public class Day18 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);
    private final List<Instruction> instr;
    int minX = 0, maxX = 0, minY = 0, maxY = 0;


    public Day18(List<String> input) {
        this(input, false);
    }

    public Day18(List<String> input, boolean part2) {
        this.instr = part2 ? parseInstructionsPart2(input) : parseInstructions(input);
    }

    protected List<Instruction> parseInstructions(List<String> plainInstructions) {
        Pattern pattern = Pattern.compile("(.) (\\d+) \\((\\S+)\\)");
        return plainInstructions.stream().map(s -> {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                return new Instruction(Direction.valueOf(matcher.group(1)), parseInt(matcher.group(2)));
            }
            throw new IllegalArgumentException("Something wrong with your input, or with your parsing!");
        }).collect(toList());
    }

    protected List<Instruction> parseInstructionsPart2(List<String> plainInstructions) {
        Pattern pattern = Pattern.compile(". \\d+ \\(#(\\S{5})(\\d)\\)");
        return plainInstructions.stream().map(s -> {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                String movesHex = matcher.group(1);
                String directionAsStringOrdinal = matcher.group(2);
                return new Instruction(Direction.values()[parseInt(directionAsStringOrdinal)], parseInt(movesHex, 16));
            }
            throw new IllegalArgumentException("Something wrong with your input, or with your parsing!");
        }).collect(toList());
    }

    public long solveWithGrid() {

        prepareMinsAndMaxs();

        int ySize = maxY - minY + 3;
        int xSize = maxX - minX + 3;
        LOGGER.info("Trying to initialize a matrix {}x{}", ySize, xSize);
        boolean[][] field = new boolean[ySize][xSize];
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], false);
        }

        int xCursor = -minX + 1;
        int yCursor = -minY + 1;


        for (Instruction i : instr) {
            LOGGER.debug("Applying {}", i);
            for (int j = 1; j <= i.moves; j++) {
                LOGGER.debug("Digging {} in ({},{})", j, xCursor, yCursor);
                switch (i.d) {
                    case L -> xCursor--;
                    case R -> xCursor++;
                    case U -> yCursor--;
                    case D -> yCursor++;
                }
                field[yCursor][xCursor] = true;
            }
        }
        LOGGER.info("Cursor {},{}", xCursor, yCursor);


        show(field);


        boolean[][] areaFilled = new boolean[ySize][maxX - minX + 3];
        for (int i = 0; i < areaFilled.length; i++) {
            Arrays.fill(areaFilled[i], false);
        }
        long areaAndTrench = 0L;
        for (int i = 0; i < field.length; i++) {
            LOGGER.info("Line {}", i);
            int crossingTimes = 0;

            Direction crossingFrom = null;
            for (int j = 0; j < field[i].length; j++) {

                if (field[i][j]) {
                    if (field[i + 1][j] && field[i - 1][j]) { // vertical trench
                        crossingTimes++;
                    } else if (!field[i + 1][j] && !field[i - 1][j]) { // horizontal trench
                        assert crossingFrom != null;
                    } else {
                        if (crossingFrom == null) {
                            if (field[i - 1][j]) {
                                crossingFrom = U;
                            } else if (field[i + 1][j]) {
                                crossingFrom = D;
                            }
                        } else {
                            if (field[i - 1][j] && crossingFrom == D) {
                                crossingTimes++;
                            } else if (field[i + 1][j] && crossingFrom == U) {
                                crossingTimes++;
                            }
                            crossingFrom = null;


                        }

                    }

                    areaAndTrench++;
                    areaFilled[i][j] = true;
                } else if (crossingTimes % 2 != 0) {
                    areaAndTrench++;
                    areaFilled[i][j] = true;
                }

            }

            assert crossingTimes % 2 == 0;
        }

        show(areaFilled);

        return areaAndTrench;
    }

    private void show(boolean[][] field) {
        System.out.println("---");
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println("---");
    }

    private void prepareMinsAndMaxs() {
        int xCursor = 0;
        int yCursor = 0;
        for (Instruction i : instr) {
            int xMove = switch (i.d) {
                case L -> i.moves * -1;
                case R -> i.moves;
                default -> 0;
            };

            xCursor += xMove;
            minX = min(minX, xCursor);
            maxX = max(maxX, xCursor);

            int yMove = switch (i.d) {
                case U -> i.moves * -1;
                case D -> i.moves;
                default -> 0;
            };

            yCursor += yMove;
            minY = min(minY, yCursor);
            maxY = max(maxY, yCursor);

        }

        LOGGER.info("minX = {}, maxX = {}, minY = {}, maxY = {}", minX, maxX, minY, maxY);
    }


    public long solveWithPick() {
        List<Point> points = prepareVertices();
        System.out.println(points);

        // By Pick theorem https://www.wikiwand.com/en/Pick%27s_theorem
        // Area in lattice = internal points + (boundary points)/2 - 1


        // => internal points = Area - (boundary points)/2 + 1

        long perimeter = instr.stream().mapToLong(i -> i.moves).sum();
        long area = (long) shoelaceArea(points);
        long innerPoints = area - (perimeter / 2) + 1;

        // => internal points + boundary points = Area + (boundary points)/2 + 1

        long areaAndBoundary = innerPoints + perimeter;

        LOGGER.info("Perimeter {}; Area {}; Internal points {}; total {}", perimeter, area, innerPoints, areaAndBoundary);
        return areaAndBoundary;
    }

    private List<Point> prepareVertices() {
        List<Point> points = new ArrayList<>();
        int xCursor = 0;
        int yCursor = 0;
        points.add(new Point(xCursor, yCursor));
        for (Instruction i : instr) {
            int xMove = switch (i.d) {
                case L -> i.moves * -1;
                case R -> i.moves;
                default -> 0;
            };
            xCursor += xMove;

            int yMove = switch (i.d) {
                case U -> i.moves * -1;
                case D -> i.moves;
                default -> 0;
            };

            yCursor += yMove;

            points.add(new Point(xCursor, yCursor));
        }

        return points;
    }


    public record Instruction(Direction d, int moves) {
        @Override
        public String toString() {
            return "%s %d".formatted(d, moves);
        }
    }

    public enum Direction {
        R, D, L, U
    }

    public static double shoelaceArea(List<Point> vertices) {
        // https://www.wikiwand.com/en/Shoelace_formula
        double area = 0L;
        for (int i = 0; i < vertices.size() - 1; i++) {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get(i + 1);

            area += p1.x * p2.y - p1.y * p2.x;
        }
        area += vertices.getLast().x * vertices.getFirst().y - vertices.getLast().y * vertices.getFirst().x;

        return abs(area) / 2;
    }


    record Point(long x, long y) {

    }
}
