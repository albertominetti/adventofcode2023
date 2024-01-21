package aminetti.adventofcode2023.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static aminetti.adventofcode2023.day17.Day17.Direction.*;

public class Day17 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);
    public static final int MAX_CONSECUTIVES = 3;
    public static final int MAX_CONSECUTIVES_PART2 = 10;
    private final List<String> field;
    private final int maxX;
    private final int maxY;


    public Day17(List<String> field) {
        this.field = field;
        maxX = field.size();
        maxY = field.getFirst().length();
        LOGGER.info("Dimensions: {}x{}", maxX, maxY);
    }


    public long solve() {
        Queue<PosConsecCost> queue = new PriorityQueue<>();
        Map<Pos, Long> distance = new HashMap<>();

        queue.add(new PosConsecCost(0, 1, 1, RIGHT, calcCost(0, 1)));
        queue.add(new PosConsecCost(1, 0, 1, DOWN, calcCost(1, 0)));

        while (!queue.isEmpty()) {
            PosConsecCost curr = queue.poll();
            Pos pos = new Pos(curr.x, curr.y, curr.consecutives, curr.d);

            if (distance.containsKey(pos)) {
                continue;
            }
            distance.put(pos, curr.cost);

            queue.addAll(nexts(curr));

        }
        return distance.entrySet().stream()
                .filter(e -> e.getKey().x == maxX - 1 && e.getKey().y == maxY - 1)
                .mapToLong(Map.Entry::getValue)
                .sorted().findFirst().getAsLong();
    }

    private List<PosConsecCost> nexts(PosConsecCost c) {
        ArrayList<Direction> dirs = new ArrayList<>();
        switch (c.d) {
            case RIGHT, LEFT -> {
                dirs.add(UP);
                dirs.add(DOWN);
            }
            case UP, DOWN -> {
                dirs.add(LEFT);
                dirs.add(RIGHT);
            }
        }

        if (c.consecutives < MAX_CONSECUTIVES) {
            dirs.add(c.d);
        }

        List<PosConsecCost> ret = new ArrayList<>();
        for (Direction d : dirs) {
            int newX = c.x + d.x;
            int newY = c.y + d.y;
            if (0 <= newX && newX < maxX && 0 <= newY && newY < maxY) {
                long cost = calcCost(newX, newY);
                int consecutives = c.d == d ? c.consecutives + 1 : 1;
                ret.add(new PosConsecCost(newX, newY, consecutives, d, c.cost + cost));
            }
        }

        LOGGER.info("nexts for {} are: {}", c, ret);

        return ret;
    }


    public long solvePart2() {
        Queue<PosConsecCost> queue = new PriorityQueue<>();
        Map<Pos, Long> distance = new HashMap<>();

        queue.add(new PosConsecCost(0, 1, 1, RIGHT, calcCost(0, 1)));
        queue.add(new PosConsecCost(1, 0, 1, DOWN, calcCost(1, 0)));

        while (!queue.isEmpty()) {
            PosConsecCost curr = queue.poll();
            Pos pos = new Pos(curr.x, curr.y, curr.consecutives, curr.d);

            if (distance.containsKey(pos)) {
                continue;
            }
            distance.put(pos, curr.cost);

            queue.addAll(nextsPart2(curr));

        }
        return distance.entrySet().stream()
                .filter(e -> {
                    Pos key = e.getKey();
                    return key.x == maxX - 1 && key.y == maxY - 1 && key.consecutives >= 4;
                })
                .mapToLong(Map.Entry::getValue)
                .sorted().findFirst().getAsLong();
    }

    private List<PosConsecCost> nextsPart2(PosConsecCost c) {
        ArrayList<Direction> dirs = new ArrayList<>();
        if (c.consecutives >= 4) {
            switch (c.d) {
                case RIGHT, LEFT -> {
                    dirs.add(UP);
                    dirs.add(DOWN);
                }
                case UP, DOWN -> {
                    dirs.add(LEFT);
                    dirs.add(RIGHT);
                }
            }
        }

        if (c.consecutives < MAX_CONSECUTIVES_PART2) {
            dirs.add(c.d);
        }

        List<PosConsecCost> ret = new ArrayList<>();
        for (Direction d : dirs) {
            int newX = c.x + d.x;
            int newY = c.y + d.y;
            if (0 <= newX && newX < maxX && 0 <= newY && newY < maxY) {
                long cost = calcCost(newX, newY);
                int consecutives = c.d == d ? c.consecutives + 1 : 1;
                ret.add(new PosConsecCost(newX, newY, consecutives, d, c.cost + cost));
            }
        }

        LOGGER.info("nexts for {} are: {}", c, ret);

        return ret;
    }


    private int calcCost(int x, int y) {
        return field.get(x).charAt(y) - '0';
    }


    public enum Direction {
        RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1), UP(-1, 0);

        public final int x;
        public final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    record PosConsecCost(int x, int y, int consecutives, Direction d, long cost) implements Comparable<PosConsecCost> {

        @Override
        public String toString() {
            return "(%d, %d, %d, %s, %d)".formatted(x, y, consecutives, d, cost);
        }

        @Override
        public int compareTo(PosConsecCost o) {
            return Comparator
                    .comparing(PosConsecCost::cost)
                    .thenComparing(PosConsecCost::consecutives)
                    .thenComparing(PosConsecCost::y)
                    .thenComparing(PosConsecCost::x)
                    .compare(this, o);
        }
    }

    record Pos(int x, int y, int consecutives, Direction d) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pos pos = (Pos) o;

            if (x != pos.x) return false;
            if (y != pos.y) return false;
            if (consecutives != pos.consecutives) return false;
            return d == pos.d;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + consecutives;
            result = 31 * result + (d != null ? d.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(%d, %d, %d, %s)".formatted(x, y, consecutives, d);
        }
    }
}

