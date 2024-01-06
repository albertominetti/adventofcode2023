package aminetti.adventofcode2023.day16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static aminetti.adventofcode2023.day16.Day16.Direction.*;

public class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);
    private final List<String> input;
    private final int rows;
    private final int cols;
    boolean[][][] visitedFrom;


    public Day16(List<String> input) {
        this.input = input;
        rows = input.size();
        cols = input.getFirst().length();
    }

    public long solve() {
        visitedFrom = new boolean[rows][cols][4];
        return enlight(0, 0, RIGHT);
    }

    public long solvePart2() {
        long maxVisited = 0;
        for (int y = 0; y < cols; y++) {
            maxVisited = Math.max(maxVisited, enlight(0, y, RIGHT));
            maxVisited = Math.max(maxVisited, enlight(rows - 1, y, LEFT));
        }

        for (int x = 0; x < rows; x++) {
            maxVisited = Math.max(maxVisited, enlight(x, 0, DOWN));
            maxVisited = Math.max(maxVisited, enlight(x, cols - 1, UP));
        }

        return maxVisited;
    }

    private long enlight(int x, int y, Direction d) {
        LOGGER.info("Starting from ({},{}) going {}", x, y, d);
        visitedFrom = new boolean[rows][cols][4];
        dfs(x, y, d);
        return howManyVisited();
    }

    private long howManyVisited() {
        long total = 0;
        for (boolean[][] booleans : visitedFrom) {
            for (boolean[] v : booleans) {
                if (v[0] || v[1] || v[2] || v[3]) total++;
            }
        }
        return total;
    }

    private void dfs(int x, int y, Direction d) {
        if (x < 0 || x >= rows) return;
        if (y < 0 || y >= cols) return;
        if (visitedFrom[x][y][d.ordinal()]) return;

        LOGGER.debug("Going {} from ({},{})", d, x, y);

        visitedFrom[x][y][d.ordinal()] = true;

        char c = input.get(y).charAt(x);

        switch (d) {
            case RIGHT -> {
                switch (c) {
                    case '.':
                    case '-':
                        dfs(x + 1, y, RIGHT);
                        break;
                    case '|':
                        dfs(x, y - 1, UP);
                        dfs(x, y + 1, DOWN);
                        break;
                    case '\\':
                        dfs(x, y + 1, DOWN);
                        break;
                    case '/':
                        dfs(x, y - 1, UP);
                        break;
                }
            }
            case DOWN -> {
                switch (c) {
                    case '.':
                    case '|':
                        dfs(x, y + 1, DOWN);
                        break;

                    case '-':
                        dfs(x + 1, y, RIGHT);
                        dfs(x - 1, y, LEFT);
                        break;
                    case '\\':
                        dfs(x + 1, y, RIGHT);
                        break;
                    case '/':
                        dfs(x - 1, y, LEFT);
                        break;
                }
            }
            case LEFT -> {

                switch (c) {
                    case '.':
                    case '-':
                        dfs(x - 1, y, LEFT);
                        break;
                    case '|':
                        dfs(x, y - 1, UP);
                        dfs(x, y + 1, DOWN);
                        break;
                    case '\\':
                        dfs(x, y - 1, UP);
                        break;
                    case '/':
                        dfs(x, y + 1, DOWN);
                        break;
                }
            }
            case UP -> {
                switch (c) {
                    case '.':
                    case '|':
                        dfs(x, y - 1, UP);
                        break;

                    case '-':
                        dfs(x + 1, y, RIGHT);
                        dfs(x - 1, y, LEFT);
                        break;
                    case '\\':
                        dfs(x - 1, y, LEFT);
                        break;
                    case '/':
                        dfs(x + 1, y, RIGHT);
                        break;
                }
            }
        }


    }

    enum Direction {RIGHT, DOWN, LEFT, UP}


}
