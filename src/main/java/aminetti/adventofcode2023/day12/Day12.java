package aminetti.adventofcode2023.day12;

import aminetti.adventofcode2023.day11.Day11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);
    private final List<Conditions> conditions;


    public Day12(List<String> input) {
        conditions = readInput(input);
    }

    public int solve() {
        return conditions.stream().mapToInt(c -> calc(c.conditions, c.groups)).sum();
    }

    public static int calc(String conditions, List<Integer> groups) {
        return calc(conditions, groups, "");
    }

    public static int calc(String s, List<Integer> groups, String current) {
        LOGGER.info("Running for: {} and {}", s, groups);
        if (s.length() == 0 && groups.isEmpty()) {
            LOGGER.info("+1 for {}", current);
            return 1;
        }
        if (s.length() > 0) {
            if (s.charAt(0) == '.') return whenOperational(s, groups, current);

            if (s.charAt(0) == '#') return whenDamaged(s, groups, current);
            if (s.charAt(0) == '?') return whenDamaged(s, groups, current) + whenOperational(s, groups, current);

        }
        return 0;
    }

    private static int whenDamaged(String s, List<Integer> groups, String current) {
        if (groups.isEmpty()) return 0;

        Integer nextGroup = groups.get(0);
        if (s.length() < nextGroup) return 0;
        if (s.substring(0, nextGroup).contains(".")) return 0;

        String newString = s.substring(nextGroup);
        if (newString.length() > 0) {
            if (newString.charAt(0) == '#') return 0;
            return calc(newString.substring(1), groups.subList(1, groups.size()), current + "#".repeat(nextGroup) + ".");
        }
        return calc(newString, groups.subList(1, groups.size()), current + "#".repeat(nextGroup));

    }

    private static int whenOperational(String s, List<Integer> groups, String current) {
        return calc(s.substring(1), new ArrayList<>(groups), current + ".");
    }

    public static List<Conditions> readInput(List<String> input) {
        List<Conditions> list = new ArrayList<>();
        for (String s : input) {
            String[] s1 = s.split(" ");

            Conditions c = new Conditions(s1[0],
                    Arrays.stream(s1[1].split(",")).map(Integer::parseInt).collect(toList()));

            list.add(c);
        }
        return list;
    }

    record Conditions(String conditions, List<Integer> groups) {

        @Override
        public String toString() {
            return conditions + " " + groups;
        }
    }
}
