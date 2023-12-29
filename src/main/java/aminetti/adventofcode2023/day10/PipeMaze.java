package aminetti.adventofcode2023.day10;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class PipeMaze {
    private static final Logger LOGGER = LoggerFactory.getLogger(PipeMaze.class);
    private static final int HARD_LIMIT = Integer.MAX_VALUE;

    private final int rows, cols;
    private final char[][] maze;

    boolean[][] loop;
    int pipeLength = 0;

    public PipeMaze(List<String> lines) {
        rows = lines.size();
        cols = lines.get(0).length();
        maze = readMaze(lines);

        loop = new boolean[maze.length][maze[0].length];
    }

    public long solvePart1() {

        Coordinate animalCoords = findAnimal();
        loop[animalCoords.row()][animalCoords.col()] = true;

        List<Coordinate> startAndEnd = findStartAndEnd(animalCoords);
        LOGGER.info("Start and end {}", startAndEnd);

        visitAndCount(startAndEnd.get(0), startAndEnd.get(1));

        return (pipeLength + 1) / 2 + 1;
    }


    public long solvePart2() {
        solvePart1();

        int area = findInnerArea();
        print();

        LOGGER.info("Area: {}", area);

        return area;
    }

    private int findInnerArea() {
        int enclosingArea = 0;
        for (int i = 0; i < maze.length; i++) {
            int crossingPipe = 0;
            Character initialHorizPipe = null;
            for (int j = 0; j < maze[0].length; j++) {

                if (!loop[i][j]) {
                    if (crossingPipe % 2 != 0) {
                        enclosingArea++;
                    }
                } else {
                    switch (maze[i][j]) {
                        case '|' -> {
                            assert initialHorizPipe == null;
                            crossingPipe++;
                        }
                        case 'F' -> initialHorizPipe = 'F';
                        case 'L' -> initialHorizPipe = 'L';
                        case '-' -> {
                            assert initialHorizPipe != null;
                        }
                        case '7' -> {
                            if (initialHorizPipe == 'L') crossingPipe++;
                            initialHorizPipe = null;
                        }
                        case 'J' -> {
                            if (initialHorizPipe == 'F') crossingPipe++;
                            initialHorizPipe = null;
                        }
                        default -> throw new IllegalArgumentException("The loop cannot contain other characters.");
                    }
                }
            }
            assert crossingPipe % 2 == 0;
        }
        return enclosingArea;
    }

    private void visitAndCount(Coordinate start, Coordinate end) {
        Coordinate c = start;
        while (!c.equals(end) && pipeLength < HARD_LIMIT) {
            LOGGER.debug("Looking in {}", c);

            loop[c.row()][c.col()] = true;
            pipeLength++;

            char currShape = maze[c.row()][c.col()];

            if (allowsNorth(currShape) && canMoveNorth(maze, c) && !loop[c.row() - 1][c.col]) {
                LOGGER.debug("Going UP.");
                c = new Coordinate(c.row() - 1, c.col());
            } else if (allowsSouth(currShape) && canMoveSouth(maze, c) && !loop[c.row() + 1][c.col]) {
                LOGGER.debug("Going DOWN.");
                c = new Coordinate(c.row() + 1, c.col());
            } else if (allowsWest(currShape) && canMoveWest(maze, c) && !loop[c.row()][c.col - 1]) {
                LOGGER.debug("Going LEFT.");
                c = new Coordinate(c.row(), c.col() - 1);
            } else if (allowsEast(currShape) && canMoveEast(maze, c) && !loop[c.row()][c.col + 1]) {
                LOGGER.debug("Going RIGHT.");
                c = new Coordinate(c.row(), c.col() + 1);
            } else {
                break;
            }
        }
        loop[end.row()][end.col()] = true;

    }

    private void print() {
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(loop[i][j] ? maze[i][j] : ".");
            }
        }
        System.out.println();
    }


    public List<Coordinate> findStartAndEnd(Coordinate c) {
        Set<Character> possibileValues = newHashSet('|', 'J', '-', 'L', '7', 'F');
        List<Coordinate> startAndEnd = new ArrayList<>(2);
        if (canMoveNorth(maze, c)) {
            startAndEnd.add(new Coordinate(c.row() - 1, c.col()));
            possibileValues.removeAll(newHashSet('-', '7', 'F'));
        }
        if (canMoveSouth(maze, c)) {
            startAndEnd.add(new Coordinate(c.row() + 1, c.col()));
            possibileValues.removeAll(newHashSet('J', '-', 'L'));
        }
        if (canMoveWest(maze, c)) {
            startAndEnd.add(new Coordinate(c.row(), c.col() - 1));
            possibileValues.removeAll(newHashSet('F', '|', 'L'));
        }
        if (canMoveEast(maze, c)) {
            startAndEnd.add(new Coordinate(c.row(), c.col() + 1));
            possibileValues.removeAll(newHashSet('|', '7', 'J'));
        }
        maze[c.row][c.col] = Iterables.getOnlyElement(possibileValues);
        return startAndEnd;
    }

    private boolean canMoveNorth(char[][] maze, Coordinate c) {
        if (c.row() > 0) {
            char north = maze[c.row() - 1][c.col()];
            return north == '7' || north == '|' || north == 'F';
        }
        return false;
    }

    private boolean canMoveSouth(char[][] maze, Coordinate c) {
        if (c.row() < maze.length - 1) {
            char south = maze[c.row() + 1][c.col()];
            return south == 'L' || south == '|' || south == 'J';
        }
        return false;
    }

    private boolean canMoveWest(char[][] maze, Coordinate c) {
        if (c.col() > 0) {
            char west = maze[c.row()][c.col() - 1];
            return west == 'F' || west == '-' || west == 'L';
        }
        return false;
    }

    private boolean canMoveEast(char[][] maze, Coordinate c) {
        if (c.col() < maze[0].length - 1) {
            char east = maze[c.row()][c.col() + 1];
            return east == 'J' || east == '-' || east == '7';
        }
        return false;
    }

    @Deprecated
    private void dfs(Coordinate c) {
        LOGGER.debug("DFS in {}", c);
        if (loop[c.row()][c.col()]) {
            LOGGER.debug("Skip {} because already visited.", c);
            return;
        }

        LOGGER.info("DFS in {}", c);

        loop[c.row()][c.col()] = true;
        LOGGER.debug("Mark {} as visited.", c);

        if (maze[c.row()][c.col()] == 'S') {
            LOGGER.info("Found the animal! Ignoring it.");
            return;
        }

        pipeLength++;

        char currShape = maze[c.row()][c.col()];

        if (allowsNorth(currShape) && canMoveNorth(maze, c)) dfs(new Coordinate(c.row() - 1, c.col()));
        if (allowsSouth(currShape) && canMoveSouth(maze, c)) dfs(new Coordinate(c.row() + 1, c.col()));
        if (allowsWest(currShape) && canMoveWest(maze, c)) dfs(new Coordinate(c.row(), c.col() - 1));
        if (allowsEast(currShape) && canMoveEast(maze, c)) dfs(new Coordinate(c.row(), c.col() + 1));

    }

    private boolean allowsNorth(char currShape) {
        return currShape == 'J' || currShape == '|' || currShape == 'L';
    }

    private boolean allowsSouth(char currShape) {
        return currShape == 'F' || currShape == '|' || currShape == '7';
    }

    private boolean allowsWest(char currShape) {
        return currShape == 'J' || currShape == '-' || currShape == '7';
    }

    private boolean allowsEast(char currShape) {
        return currShape == 'L' || currShape == '-' || currShape == 'F';
    }

    public static char[][] readMaze(List<String> lines) {
        char[][] maze = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
        }

        return maze;
    }

    public Coordinate findAnimal() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                //LOGGER.info("Position: {},{}", i, j);
                if (maze[i][j] == 'S') return new Coordinate(i, j);
            }
        }
        throw new IllegalArgumentException("The animal is not here!");
    }

    public record Coordinate(int row, int col) {
        @Override
        public String toString() {
            return "(%d,%d)".formatted(row, col);
        }
    }
}
