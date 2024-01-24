package aminetti.adventofcode2023.day18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static aminetti.adventofcode2023.day18.Day18.Direction.*;
import static aminetti.adventofcode2023.day18.Day18.Direction.U;
import static java.util.stream.Collectors.toList;

public class Day18 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);
    private final List<Instruction> instr;
    int minX = 0, maxX = 0, minY = 0, maxY = 0;


    public Day18(List<String> input) {
        this.instr = parseInstructions(input);
        prepareMinsAndMaxs();
    }

    private List<Instruction> parseInstructions(List<String> plainInstructions) {
        Pattern pattern = Pattern.compile("(.) (\\d+) \\((\\S+)\\)");
        return plainInstructions.stream().map(s -> {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                return new Instruction(Direction.valueOf(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));
            }
            throw new IllegalArgumentException("Something wrong with your input, or with your parsing!");
        }).collect(toList());
    }

    public long solve() {
        char[][] field = new char[maxY - minY + 3][maxX - minX + 3];
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], '.');
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
                field[yCursor][xCursor] = '#';
            }
        }
        LOGGER.info("Cursor {},{}", xCursor, yCursor);


        show(field);


        char[][] areaFilled = new char[maxY - minY + 3][maxX - minX + 3];
        for (int i = 0; i < areaFilled.length; i++) {
            Arrays.fill(areaFilled[i], '.');
        }
        long areaAndTrench = 0L;
        for (int i = 0; i < field.length; i++) {
            LOGGER.info("Line {}", i);
            int crossingTimes = 0;

            Direction crossingFrom = null;
            for (int j = 0; j < field[i].length; j++) {

                if (field[i][j] == '#') {
                    if (field[i + 1][j] == '#' && field[i - 1][j] == '#') { // vertical trench
                        crossingTimes++;
                    } else if (field[i + 1][j] != '#' && field[i - 1][j] != '#') { // horizontal trench
                        assert crossingFrom != null;
                    } else {
                        if (crossingFrom == null) {
                            if (field[i - 1][j] == '#') {
                                crossingFrom = U;
                            } else if (field[i + 1][j] == '#') {
                                crossingFrom = D;
                            }
                        } else {
                            if (field[i - 1][j] == '#' && crossingFrom == D) {
                                crossingTimes++;
                            } else if (field[i + 1][j] == '#' && crossingFrom == U) {
                                crossingTimes++;
                            }
                            crossingFrom = null;


                        }

                    }

                    areaAndTrench++;
                    areaFilled[i][j] = '#';
                } else if (crossingTimes % 2 != 0) {
                    areaAndTrench++;
                    areaFilled[i][j] = '#';
                }

            }

            assert crossingTimes % 2 == 0;
        }

        show(areaFilled);

        return areaAndTrench;
    }

    private void show(char[][] field) {
        System.out.println("---");
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
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
            minX = Math.min(minX, xCursor);
            maxX = Math.max(maxX, xCursor);

            int yMove = switch (i.d) {
                case U -> i.moves * -1;
                case D -> i.moves;
                default -> 0;
            };

            yCursor += yMove;
            minY = Math.min(minY, yCursor);
            maxY = Math.max(maxY, yCursor);

        }

        LOGGER.info("minX = {}, maxX = {}, minY = {}, maxY = {}", minX, maxX, minY, maxY);
    }


    public record Instruction(Direction d, int moves, String color) {
        @Override
        public String toString() {
            return "%s %d %s".formatted(d, moves, color);
        }
    }

    public enum Direction {
        U, D, L, R
    }
}
