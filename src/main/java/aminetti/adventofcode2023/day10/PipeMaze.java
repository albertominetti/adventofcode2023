package aminetti.adventofcode2023.day10;

import aminetti.adventofcode2023.day03.MissingPartForEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PipeMaze {
    private static final Logger LOGGER = LoggerFactory.getLogger(PipeMaze.class);
    private static final int HARD_LIMIT = Integer.MAX_VALUE;

    int rows, cols;


    boolean[][] visited, visited2;
    int[][] groups;
    Map<Integer, Integer> groupSize;
    Set<Integer> externalGroups;
    int pipeLength = 0;
    char[][] maze;

    public long solvePart1(List<String> lines) {
        maze = readMaze(lines);
        visited = new boolean[maze.length][maze[0].length];

        Coordinate animalCoords = findAnimal(maze);
        visited[animalCoords.row()][animalCoords.col()] = true;

        List<Coordinate> startAndEnd = findStartAndEnd(maze, animalCoords);
        LOGGER.debug("Start and end {}", startAndEnd);

        visitAndCount(startAndEnd);

        return (pipeLength + 1) / 2 + 1;
    }


    public long solvePart2(List<String> lines) {
        solvePart1(lines);

        return findEnclosedArea();
    }

    private int findEnclosedArea() {
        visited2 = new boolean[maze.length][maze[0].length];
        for (int i = 0; i < visited.length; i++)
            visited2[i] = visited[i].clone();

        groups = new int[maze.length][maze[0].length];
        groupSize = new HashMap<>();
        externalGroups = new HashSet<>();

        int group = 0;
        int totalInnerSize = 0;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (visited2[i][j]) continue;
                Coordinate c = new Coordinate(i, j);
                LOGGER.info("Starting to visit {} for group {}", c, group);
                int size = dfs2(c, group);
                groupSize.put(group, size);
                if (!externalGroups.contains(group)) {
                    LOGGER.info("Found a group {} internal to pipes, with size {}", group, size);
                    totalInnerSize += size;
                }

                group++;

            }
        }

        LOGGER.info("Total groups: {}", group);
        Map<Integer, Integer> groupSizeFilteredOnlyInternals = groupSize.entrySet().stream()
                .filter(e -> !externalGroups.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        LOGGER.info("Group sizes for internal groups {}", groupSizeFilteredOnlyInternals);
        LOGGER.info("External groups: {}", externalGroups);
        LOGGER.info("Total size of internal groups: {}", totalInnerSize);

        System.out.println("TOT: " + groupSizeFilteredOnlyInternals.values().stream().mapToInt(z -> z).sum());

        return totalInnerSize;
    }

    private int dfs2(Coordinate c, int g) {

        LOGGER.info("Checking {} for group {}", c, g);

        // responsible for: grouping the spaces; count size; check if external groups
        int row = c.row();
        int col = c.col();
        if (visited2[row][col]) return 0;
        visited2[row][col] = true;

        groups[row][col] = g; // set the group

        List<Coordinate> directions = List.of(
                new Coordinate(-1, -1), new Coordinate(-1, 0), new Coordinate(-1, 1),
                new Coordinate(0, -1), new Coordinate(0, 1),
                new Coordinate(1, -1), new Coordinate(1, 0), new Coordinate(1, 1)
        );

        int nearBySize = 0;
        for (Coordinate direction : directions) {

            int i = row - direction.row();
            int j = col - direction.col();

            if (i < 0 || i >= rows || j < 0 || j >= cols) {
                LOGGER.info("Group {} is marked as external", g);
                externalGroups.add(g); // mark as external group
                break;
            }

            if (!visited2[i][j]) {
                nearBySize += dfs2(new Coordinate(i, j), g); // count the near tiles
            }
        }


        return nearBySize + 1;
    }

    private void visitAndCount(List<Coordinate> startAndEnd) {
        Coordinate start = startAndEnd.get(0);
        visited[start.row()][start.col()] = true;
        Coordinate end = startAndEnd.get(1);
        visited[end.row()][end.col()] = true;
        Coordinate c = start;
        while (!c.equals(end) && pipeLength < HARD_LIMIT) {
            LOGGER.debug("Looking in {}", c);

            visited[c.row()][c.col()] = true;
            pipeLength++;

            char currShape = maze[c.row()][c.col()];

            if (allowsNorth(currShape) && canMoveNorth(maze, c) && !visited[c.row() - 1][c.col]) {
                LOGGER.debug("Going UP.");
                c = new Coordinate(c.row() - 1, c.col());
            } else if (allowsSouth(currShape) && canMoveSouth(maze, c) && !visited[c.row() + 1][c.col]) {
                LOGGER.debug("Going DOWN.");
                c = new Coordinate(c.row() + 1, c.col());
            } else if (allowsWest(currShape) && canMoveWest(maze, c) && !visited[c.row()][c.col - 1]) {
                LOGGER.debug("Going LEFT.");
                c = new Coordinate(c.row(), c.col() - 1);
            } else if (allowsEast(currShape) && canMoveEast(maze, c) && !visited[c.row()][c.col + 1]) {
                LOGGER.debug("Going RIGHT.");
                c = new Coordinate(c.row(), c.col() + 1);
            } else {
                break;
            }
        }

        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(visited[i][j] ? "8" : " ");
            }
        }
        System.out.println();
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
        rows = lines.size();
        cols = lines.get(0).length();
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
