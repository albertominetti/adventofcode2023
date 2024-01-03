package aminetti.adventofcode2023.day13;

import aminetti.adventofcode2023.day11.Day11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);
    private final List<List<String>> fields;

    public Day13(List<String> input) {
        fields = readInput(input);
    }

    public long solve() {
        long total = 0;
        for (int i = 0; i < fields.size(); i = i + 1) {
            total += calc(fields.get(i));
        }
        return total;
    }

    public long solvePart2() {
        long total = 0;
        for (int i = 0; i < fields.size(); i = i + 1) {
            total += calc2(fields.get(i));
        }
        return total;
    }

    public static long calc(List<String> f) {
        int ROWS = f.size();
        int COLS = f.get(0).length();

        LOGGER.info("Searching the mirror col with {}", f);

        boolean same = false;
        int mirrorCol = 1;
        for (mirrorCol = 1; mirrorCol < COLS; mirrorCol++) {
            same = true;
            for (int gap = 1; gap <= Math.min(mirrorCol, COLS - mirrorCol); gap++) {
                final int leftCol = mirrorCol - gap;
                final int rightCol = mirrorCol + gap - 1;
                same &= IntStream.range(0, ROWS).allMatch(i ->
                        f.get(i).charAt(leftCol) == f.get(i).charAt(rightCol));
                LOGGER.info("Left {} and Right {} are the same? {}", leftCol, rightCol, same);
                if (!same) break;
            }
            if (same) break;
        }
        if (same == true) {
            LOGGER.info("Mirror colums is {}", mirrorCol);
            return mirrorCol;
        }

        LOGGER.info("Searching the mirror row with {}", f);
        int mirrorRow = 1;
        for (mirrorRow = 1; mirrorRow < ROWS; mirrorRow++) {
            same = true;
            for (int gap = 1; gap <= Math.min(mirrorRow, ROWS - mirrorRow); gap++) {
                final int aboveCol = mirrorRow - gap;
                final int belowCol = mirrorRow + gap - 1;
                same &= IntStream.range(0, COLS).allMatch(j ->
                        f.get(aboveCol).charAt(j) == f.get(belowCol).charAt(j));
                LOGGER.info("Above {} and Below {} are the same? {}", aboveCol, belowCol, same);
                if (!same) break;
            }
            if (same) break;
        }
        LOGGER.info("Mirror row is {}", mirrorRow);
        return 100L * (mirrorRow);

    }


    public static long calc2(List<String> f) {
        int ROWS = f.size();
        int COLS = f.get(0).length();

        LOGGER.info("Searching the mirror col with {}", f);

        int smudges = 0;
        int mirrorCol = 1;
        for (mirrorCol = 1; mirrorCol < COLS; mirrorCol++) {
            smudges = 0;
            for (int gap = 1; gap <= Math.min(mirrorCol, COLS - mirrorCol); gap++) {
                final int leftCol = mirrorCol - gap;
                final int rightCol = mirrorCol + gap - 1;
                smudges += IntStream.range(0, ROWS).filter(i ->
                                f.get(i).charAt(leftCol) != f.get(i).charAt(rightCol))
                        .count();
                LOGGER.info("Left {} and Right {} composite differs by {}", leftCol, rightCol, smudges);
                if (smudges > 1) break;
            }
            if (smudges == 1) break;
        }
        if (smudges == 1) {
            LOGGER.info("Mirror colums is {}", mirrorCol);
            return mirrorCol;
        }

        LOGGER.info("Searching the mirror row with {}", f);
        int mirrorRow = 1;
        for (mirrorRow = 1; mirrorRow < ROWS; mirrorRow++) {
            smudges = 0;
            for (int gap = 1; gap <= Math.min(mirrorRow, ROWS - mirrorRow); gap++) {
                final int aboveCol = mirrorRow - gap;
                final int belowCol = mirrorRow + gap - 1;
                smudges += IntStream.range(0, COLS).filter(j ->
                        f.get(aboveCol).charAt(j) != f.get(belowCol).charAt(j)).count();
                LOGGER.info("Above {} and Below {} composite differs by {}", aboveCol, belowCol, smudges);
                if (smudges > 1) break;
            }
            if (smudges == 1) break;
        }
        LOGGER.info("Mirror row is {}", mirrorRow);
        return 100L * (mirrorRow);

    }

    public static List<List<String>> readInput(List<String> input) {
        List<List<String>> fields = new ArrayList<>();
        List<String> field = new ArrayList<>();
        for (String s : input) {
            if (s.isEmpty()) {
                fields.add(field);
                field = new ArrayList<>();
            } else {
                field.add(s);
            }
        }
        fields.add(field);
        return fields;
    }
}
