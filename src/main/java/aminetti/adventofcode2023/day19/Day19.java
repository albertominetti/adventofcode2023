package aminetti.adventofcode2023.day19;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println(workflows);

        System.out.println("---");
        parts = parseParts(list.subList(split + 1, list.size()));
        System.out.println(parts);
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
        ArrayList<Part> goodParts = new ArrayList<>();
        for (Part p : parts) {
            Workflow firstWf = firstWf = workflows.get("in");
//                    workflows.values().stream()
//                    .filter(wf -> wf.test(p))
//                    .findFirst().orElseThrow();

            Workflow wf = firstWf;
            do {
                String nextName = wf.nextWorkflowName(p);
                wf = workflows.get(nextName);
            } while (!wf.isAccepted() && !wf.isRejected());

            if (wf.isAccepted()) {
                goodParts.add(p);
            }

        }

        LOGGER.info("Good parts: {}", goodParts);
        return goodParts.stream().mapToLong(Part::sumProps).sum();
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
            LOGGER.info("Checking the sf {} for {}", this, p);
            for (Rule rule : rules) {
                if (rule.prop == null || rule.test(p)) {
                    return rule.dest;
                }
            }
            throw new IllegalArgumentException("I would expect a good input");
        }
    }

    private record Rule(String prop, char op, int i, String dest) {

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
