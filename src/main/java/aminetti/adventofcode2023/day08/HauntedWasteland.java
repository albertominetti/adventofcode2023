package aminetti.adventofcode2023.day08;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static aminetti.adventofcode2023.day08.HauntedWasteland.LeftRight.of;

public class HauntedWasteland {
    private static final Logger LOGGER = LoggerFactory.getLogger(HauntedWasteland.class);

    public char[] getMoves(String movesInList) {
        return movesInList.toCharArray();
    }

    public Map<String, LeftRight> readMap(List<String> lines) {
        HashMap<String, LeftRight> map = new HashMap<>();
        Pattern pattern = Pattern.compile("(.{3}) = \\((.{3}), (.{3})\\)");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                map.put(matcher.group(1), of(matcher.group(2), matcher.group(3)));
            } else {
                throw new IllegalArgumentException("Hey, something wrong with your input!");
            }

        }

        return map;
    }

    @Deprecated // too slow!
    public long solvePart2MovingSynchnonouslyInThePaths(List<String> lines) {
        char[] moves = getMoves(lines.get(0));
        LOGGER.info("Expected moves: {}", moves);

        Map<String, LeftRight> map = readMap(lines.subList(2, lines.size()));
        LOGGER.debug("Map: {}", map);

        int steps = 0;

        List<String> currPositions = map.keySet().stream().filter(HauntedWasteland::ghostStartPosition).collect(Collectors.toList());
        LOGGER.info("Current positions {}", currPositions);

        int i = 0;
        while (!allAtEndingPosition(currPositions)) {
            char move = moves[i];
            i = (i + 1) % moves.length;

            currPositions = currPositions.stream()
                    .unordered().parallel()
                    .map(p -> nextPosition(map, p, move))
                    .collect(Collectors.toList());

            steps++;
        }


        return steps;
    }


    public BigInteger solvePart2UsingTheLeastCommonMultiple(List<String> lines) {
        char[] moves = getMoves(lines.get(0));
        LOGGER.info("Expected moves: {}", moves);

        Map<String, LeftRight> map = readMap(lines.subList(2, lines.size()));
        LOGGER.debug("Map: {}", map);

        List<String> currPositions = map.keySet().stream().filter(HauntedWasteland::ghostStartPosition).collect(Collectors.toList());
        LOGGER.info("Current positions {}", currPositions);

        return currPositions.stream()
                .unordered().parallel()
                .map(p -> countSteps(map, moves, p, HauntedWasteland::ghostEndPosition))
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, HauntedWasteland::lcm);

    }

    public static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }

    @Deprecated
    private boolean allAtEndingPosition(List<String> currPositions) {
        return currPositions.stream().allMatch(HauntedWasteland::ghostEndPosition);
    }

    private static boolean ghostEndPosition(String p) {
        return p.matches("..Z");
    }

    private static boolean ghostStartPosition(String p) {
        return p.matches("..A");
    }

    public long solve(List<String> lines) {
        char[] moves = getMoves(lines.get(0));
        LOGGER.info("Expected moves: {}", moves);

        Map<String, LeftRight> map = readMap(lines.subList(2, lines.size()));
        LOGGER.debug("Map: {}", map);

        String currPosition = "AAA";

        return countSteps(map, moves, currPosition, (p) -> StringUtils.equals(p, "ZZZ"));
    }

    private int countSteps(Map<String, LeftRight> map, char[] moves, String currPosition,
                           Predicate<String> isEndingPosition) {
        int steps = 0;

        LeftRight currCrossroad = map.get(currPosition);
        LOGGER.info("Current position {} and crossroad {}", currPosition, currCrossroad);

        int i = 0;
        while (!isEndingPosition.test(currPosition)) {
            char move = moves[i];
            i = (i + 1) % moves.length;
            currPosition = nextPosition(map, currPosition, move);
            steps++;
        }
        return steps;
    }

    private String nextPosition(Map<String, LeftRight> map, String currPosition, char move) {
        LeftRight currCrossroad = map.get(currPosition);
        if (move == 'L') {
            LOGGER.info("Moving Left...");
            currPosition = currCrossroad.getLeft();
        } else {
            LOGGER.info("Moving Right...");
            currPosition = currCrossroad.getRight();
        }
        currCrossroad = map.get(currPosition);
        LOGGER.info("Current position {} and crossroad {}", currPosition, currCrossroad);
        return currPosition;
    }

    public static class LeftRight extends MutablePair<String, String> {

        private LeftRight(String left, String right) {
            super(left, right);
        }

        public static LeftRight of(String left, String right) {
            return new LeftRight(left, right);
        }
    }

}
