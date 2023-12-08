package aminetti.adventofcode2023.day08;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aminetti.adventofcode2023.day08.HauntedWasteland.LeftRight.*;
import static java.util.stream.Collectors.groupingBy;

public class HauntedWasteland {
    private static final Logger LOGGER = LoggerFactory.getLogger(HauntedWasteland.class);

    public char[] getMoves(String movesInList) {
        return movesInList.toCharArray();
    }

    public Map<String, LeftRight> readMap(List<String> lines) {
        HashMap<String, LeftRight> map = new HashMap<>();
        Pattern pattern = Pattern.compile("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)");

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


    public long solve(List<String> lines) {
        char[] moves = getMoves(lines.get(0));
        LOGGER.info("Expected moves: {}", moves);

        Map<String, LeftRight> map = readMap(lines.subList(2, lines.size()));
        LOGGER.debug("Map: {}", map);

        int steps = 0;
        String currPosition = "AAA";
        LeftRight currCrossroad = map.get(currPosition);
        LOGGER.info("Current position {} and crossroad {}", currPosition, currCrossroad);

        int i = 0;
        while (!StringUtils.equals(currPosition, "ZZZ")) {
            char move = moves[i];
            i = (i + 1) % moves.length;
            if (move == 'L') {
                LOGGER.info("Moving Left...");
                currPosition = currCrossroad.getLeft();

            } else {
                LOGGER.info("Moving Right...");
                currPosition = currCrossroad.getRight();
            }
            currCrossroad = map.get(currPosition);
            LOGGER.info("Current position {} and crossroad {}", currPosition, currCrossroad);
            steps++;

        }


        return steps;
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
