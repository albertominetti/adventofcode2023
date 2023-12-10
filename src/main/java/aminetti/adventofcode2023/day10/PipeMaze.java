package aminetti.adventofcode2023.day10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PipeMaze {
    private static final Logger LOGGER = LoggerFactory.getLogger(PipeMaze.class);
    private static final int HARD_LIMIT = Integer.MAX_VALUE;

    public char[] getMoves(String movesInList) {
        return movesInList.toCharArray();
    }


    public long solve(List<String> lines) {
        char[] moves = getMoves(lines.get(0));
        LOGGER.info("Expected moves: {}", moves);
        return 0;
    }

    boolean[][] visited;
    int pipeLength = 0;
    char[][] maze;

    public long solvePart1(List<String> lines) {
        maze = readMaze(lines);
        Coordinate animalCoords = findAnimal(maze);

        visited = new boolean[maze.length][maze[0].length];


        List<Coordinate> startAndEnd = findStartAndEnd(maze, animalCoords);
        LOGGER.info("Start and end {}", startAndEnd);

        //dfs(start);


        Coordinate start = startAndEnd.get(0);
        Coordinate end = startAndEnd.get(1);
        Coordinate c = start;
        while (!c.equals(end) && pipeLength < HARD_LIMIT) {
            LOGGER.info("Looking in {}", c);

            visited[c.row()][c.col()] = true;
            pipeLength++;

            char currShape = maze[c.row()][c.col()];

            if (allowsNorth(currShape) && canMoveNorth(maze, c) && !visited[c.row() - 1][c.col]) {
                LOGGER.info("Going UP.");
                c = new Coordinate(c.row() - 1, c.col());
            } else if (allowsSouth(currShape) && canMoveSouth(maze, c) && !visited[c.row() + 1][c.col]) {
                LOGGER.info("Going DOWN.");
                c = new Coordinate(c.row() + 1, c.col());
            } else if (allowsWest(currShape) && canMoveWest(maze, c) && !visited[c.row()][c.col - 1]) {
                LOGGER.info("Going LEFT.");
                c = new Coordinate(c.row(), c.col() - 1);
            } else if (allowsEast(currShape) && canMoveEast(maze, c) && !visited[c.row()][c.col + 1]) {
                LOGGER.info("Going RIGHT.");
                c = new Coordinate(c.row(), c.col() + 1);
            } else {
                break;
            }


        }

        return (pipeLength + 1) / 2 + 1;
    }

    @Deprecated
    public Coordinate findOnePipeNearAnimal(char[][] maze, Coordinate c) {
        if (canMoveNorth(maze, c)) return new Coordinate(c.row() - 1, c.col());
        if (canMoveSouth(maze, c)) return new Coordinate(c.row() + 1, c.col());
        if (canMoveWest(maze, c)) return new Coordinate(c.row(), c.col() - 1);
        if (canMoveEast(maze, c)) return new Coordinate(c.row(), c.col() + 1);
        throw new IllegalArgumentException("The animal can't move.");
    }

    public List<Coordinate> findStartAndEnd(char[][] maze, Coordinate c) {
        List<Coordinate> startAndEnd = new ArrayList<>(2);
        if (canMoveNorth(maze, c)) startAndEnd.add(new Coordinate(c.row() - 1, c.col()));
        if (canMoveSouth(maze, c)) startAndEnd.add(new Coordinate(c.row() + 1, c.col()));
        if (canMoveWest(maze, c)) startAndEnd.add(new Coordinate(c.row(), c.col() - 1));
        if (canMoveEast(maze, c)) startAndEnd.add(new Coordinate(c.row(), c.col() + 1));
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
        if (visited[c.row()][c.col()]) {
            LOGGER.debug("Skip {} because already visited.", c);
            return;
        }

        LOGGER.info("DFS in {}", c);

        visited[c.row()][c.col()] = true;
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

    public char[][] readMaze(List<String> lines) {
        char[][] maze = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
        }

        return maze;
    }

    public Coordinate findAnimal(char[][] maze) {
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
