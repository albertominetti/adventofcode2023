package aminetti.adventofcode2023.day12;

import aminetti.adventofcode2023.day11.Day11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);
    private List<Conditions> conditions;
    private Map<Key, Long> cache = new HashMap<>();


    public Day12(List<String> input) {
        conditions = readInput(input);
    }

    public long solvePart2(int unfoldRatio) {
        List<Conditions> list = new ArrayList<>();
        for (Conditions c : conditions) {
            Conditions c2 = new Conditions(
                    String.join("?", Collections.nCopies(unfoldRatio, c.conditions)),
                    Collections.nCopies(unfoldRatio, c.groups).stream().flatMap(List::stream).toList());

            list.add(c2);
        }
        conditions = list;
        return solve();
    }

    public long solve() {
        cache = new HashMap<>();
        return conditions.stream().mapToLong(c -> calc(c.conditions, c.groups)).sum();
    }

    public long calc(String conditions, List<Integer> groups) {
        return calc(conditions, groups, 0, "");
    }

    public long calc(String s, List<Integer> groups, int gIndex, String current) {
        Key k = new Key(s, groups, gIndex);
        Long result = cache.get(k);
        if (result == null) {

            LOGGER.trace("Running for: {} and {}", s, groups);
            if (s.length() == 0 && groups.size() == gIndex) {
                LOGGER.debug("+1 for {}", current);
                result = 1L;
            } else if (s.length() == 0 && groups.size() > gIndex) {
                result = 0L;
            } else if (s.length() > 0) {
                if (s.charAt(0) == '.') {
                    result = whenOperational(s, groups, gIndex, current);
                } else if (s.charAt(0) == '#') {
                    result = whenDamaged(s, groups, gIndex, current);
                } else if (s.charAt(0) == '?') {
                    result = whenDamaged(s, groups, gIndex, current) + whenOperational(s, groups, gIndex, current);
                } else {
                    throw new IllegalArgumentException("Unexpected input");
                }
            }
            cache.put(k, result);
        }
        return result;
    }

    private long whenDamaged(String s, List<Integer> groups, int gIndex, String current) {
        if (groups.size() == gIndex) return 0;

        Integer nextGroup = groups.get(gIndex);
        if (s.length() < nextGroup) return 0;
        if (s.substring(0, nextGroup).contains(".")) return 0;

        String newString = s.substring(nextGroup);
        if (newString.length() > 0) {
            if (newString.charAt(0) == '#') return 0;
            return calc(newString.substring(1), groups, gIndex + 1, current + "#".repeat(nextGroup) + ".");
        }
        return calc(newString, groups, gIndex + 1, current + "#".repeat(nextGroup));

    }

    private long whenOperational(String s, List<Integer> groups, int gIndex, String current) {
        return calc(s.substring(1), groups, gIndex, current + ".");
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

    record Key(String conditions, List<Integer> groups, Integer gIndex) {
    }
}
