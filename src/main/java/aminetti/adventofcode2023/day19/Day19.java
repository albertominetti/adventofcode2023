package aminetti.adventofcode2023.day19;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day19 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);
    private final List<Part> parts;
    private final Map<String, Workflow> workflows;

    public Day19(List<String> list) {
        int split = list.indexOf("");

        workflows = parseRules(list.subList(0, split));
        workflows.put("A", new Workflow("A"));
        workflows.put("R", new Workflow("R"));
        //System.out.println(workflows);

        //System.out.println("---");
        parts = parseParts(list.subList(split + 1, list.size()));
        //System.out.println(parts);
    }

    private Map<String, Workflow> parseRules(List<String> strings) {
        Pattern pattern1 = Pattern.compile("([a-z]+)\\{.*,(\\S+)\\}"); // should be static
        Pattern pattern2 = Pattern.compile("([a-z])([<>])(\\d+):([a-zA-Z]+),"); // should be static

        Map<String, Workflow> wfs = new LinkedHashMap<>();
        for (String s : strings) {
            Matcher matcher1 = pattern1.matcher(s);
            if (!matcher1.matches()) {
                throw new IllegalArgumentException("Can't get workflow details");
            }
            String finalDestination = matcher1.group(2);
            Workflow wf = new Workflow(matcher1.group(1));
            wfs.put(wf.name, wf);

            Matcher matcher2 = pattern2.matcher(s);
            while (matcher2.find()) {
                String prop = matcher2.group(1);
                String op = matcher2.group(2);
                String value = matcher2.group(3);
                String dest = matcher2.group(4);
                wf.add(new Rule(prop, op.charAt(0), parseInt(value), dest));
            }
            wf.add(new Rule(null, ' ', 0, finalDestination));
        }

        return wfs;
    }

    private List<Part> parseParts(List<String> strings) {
        Pattern pattern = Pattern.compile("([a-z])=(\\d+)"); // should be static

        List<Part> parts = new ArrayList<>();
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            Map<String, Integer> properties = new HashMap<>();
            while (matcher.find()) {
                properties.put(matcher.group(1), parseInt(matcher.group(2)));
            }
            parts.add(new Part(properties));
        }

        return parts;
    }

    public long solve() {
        return solve(parts.stream());
    }

    private long solve(Stream<Part> parts) {
        return parts.unordered().parallel().filter(p -> {

            Workflow wf = workflows.get("in");
            do {
                String nextName = wf.nextWorkflowName(p);
                wf = workflows.get(nextName);
            } while (!wf.isAccepted() && !wf.isRejected());

            return wf.isAccepted();
        }).mapToLong(Part::sumProps).sum();
    }

    @Deprecated // too expensive =)
    public long solveWithAllPossibleParts() {
        Stream<Part> allParts = IntStream.range(1, 4000).boxed().flatMap(x ->
                IntStream.range(1, 4000).boxed().flatMap(m ->
                        IntStream.range(1, 4000).boxed().flatMap(a ->
                                IntStream.range(1, 4000).boxed().flatMap(s ->
                                        Stream.of(new Part(Map.of("x", x, "m", m, "a", a, "s", s)))))));
        return solve(allParts);
    }


    public long solveWithMetaParts() {
        RangePart rangePart = new RangePart("in");

        Deque<RangePart> workQueue = new LinkedList<>();
        workQueue.add(rangePart);

        List<RangePart> goodRanges = new ArrayList<>();
        while (!workQueue.isEmpty()) {
            RangePart curr = workQueue.pop();
            Workflow wf = workflows.get(curr.nextWf);

            if (wf.isAccepted()) {
                goodRanges.add(curr);
                continue;
            }

            if (wf.isRejected()) {
                continue;
            }

            for (Rule r : wf.rules) {
                if (r.prop == null) {
                    curr.nextWf = r.dest;
                    workQueue.add(curr);
                } else if (r.op == '<') {
                    RangePart matchingRange = curr.splitLow(r.prop, r.i - 1);
                    matchingRange.nextWf = r.dest;
                    workQueue.add(matchingRange);

                    RangePart notMathingRange = curr.splitHigh(r.prop, r.i);
                    curr = notMathingRange;
                } else {
                    RangePart matchingRange = curr.splitHigh(r.prop, r.i + 1);
                    matchingRange.nextWf = r.dest;
                    workQueue.add(matchingRange);

                    RangePart notMathingRange = curr.splitLow(r.prop, r.i);
                    curr = notMathingRange;
                }
            }
        }

        LOGGER.info("Good ranges: {}", goodRanges);
        return goodRanges.stream().mapToLong(RangePart::totalCombinations).sum();
    }

    public static class RangePart {
        public static final long MAX_VALUE = 4000L;

        public String nextWf;
        public Map<String, Long> min;
        public Map<String, Long> max;

        public RangePart(RangePart other) {
            this.max = new HashMap<>(other.max);
            this.min = new HashMap<>(other.min);
        }

        public RangePart(String nextWf) {
            this.nextWf = nextWf;
            min = Map.of("x", 1L, "m", 1L, "a", 1L, "s", 1L);
            max = Map.of("x", MAX_VALUE, "m", MAX_VALUE, "a", MAX_VALUE, "s", MAX_VALUE);
        }

        public RangePart splitLow(String prop, long newMax) {
            RangePart rangePart = new RangePart(this);
            rangePart.max.put(prop, newMax);
            return rangePart;
        }

        public RangePart splitHigh(String prop, long newMin) {
            RangePart rangePart = new RangePart(this);
            rangePart.min.put(prop, newMin);
            return rangePart;
        }

        public long totalCombinations() {
            return sizeOfRangeByProp("x") * sizeOfRangeByProp("a")
                    * sizeOfRangeByProp("m") * sizeOfRangeByProp("s");
        }

        // not really required
        public long totalSumOfCombinationsWithMath() {
            System.out.println(this);
            return sumByProp("x") * (totalCombinations() / sizeOfRangeByProp("x"))
                    + sumByProp("s") * (totalCombinations() / sizeOfRangeByProp("s"))
                    + sumByProp("a") * (totalCombinations() / sizeOfRangeByProp("a"))
                    + sumByProp("m") * (totalCombinations() / sizeOfRangeByProp("m"));
        }

        // not really required
        private long sizeOfRangeByProp(String prop) {
            return (max.get(prop) - min.get(prop)) + 1;
        }

        // not really required
        private long sumByProp(String prop) {
            return sumFirstNums(max.get(prop)) - sumFirstNums(min.get(prop) - 1L);
        }

        // not really required
        public static long sumFirstNums(long x) {
            return (x * (x + 1L)) / 2;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", RangePart.class.getSimpleName() + "[", "]")
                    .add("nextWf='" + nextWf + "'")
                    .add("min=" + min)
                    .add("max=" + max)
                    .toString();
        }
    }

    public static class Part {
        public final Map<String, Integer> properties;

        public Part(Map<String, Integer> properties) {
            this.properties = properties;
        }

        public long sumProps() {
            return properties.values().stream().mapToLong(v -> v).sum();
        }

        @Override
        public String toString() {
            return properties.toString();
        }
    }

    public static class Workflow {
        String name;
        List<Rule> rules = new ArrayList<>();

        public Workflow(String name) {
            this.name = name;
        }

        public void add(Rule rule) {
            rules.add(rule);
        }

        public boolean test(Part p) {
            return rules.stream().anyMatch(r -> r.test(p));
        }

        @Override
        public String toString() {
            return "%s{%s}".formatted(name, rules);
        }

        public boolean isAccepted() {
            return StringUtils.equals("A", name);
        }

        public boolean isRejected() {
            return StringUtils.equals("R", name);
        }

        public String nextWorkflowName(Part p) {
            LOGGER.debug("Checking the sf {} for {}", this, p);
            for (Rule rule : rules) {
                if (rule.prop == null || rule.test(p)) {
                    return rule.dest;
                }
            }
            throw new IllegalArgumentException("I would expect a good input");
        }
    }

    public record Rule(String prop, char op, int i, String dest) {

        public boolean test(Part p) {
            if (prop == null) return false;

            if (p.properties.containsKey(prop)) {
                Integer val = p.properties.get(prop);
                if (op == '<' && val < i || op == '>' && val > i) {
                    return true;
                }
            }
            return false;

        }

        @Override
        public String toString() {
            if (prop == null) {
                return ":%s".formatted(dest);

            }
            return "%s%s%d:%s".formatted(prop, op, i, dest);
        }
    }

}
