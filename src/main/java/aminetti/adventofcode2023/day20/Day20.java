package aminetti.adventofcode2023.day20;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static aminetti.adventofcode2023.day20.Day20.Pulse.HIGH;
import static aminetti.adventofcode2023.day20.Day20.Pulse.LOW;

public class Day20 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20.class);

    private final Map<String, M> modules = new HashMap<>();
    private final Deque<Signal> queue = new LinkedList();

    private Long currentRun = 0L;
    private Map<Long, Long> lows = new HashMap<>();
    private Map<Long, Long> highs = new HashMap<>();


    Conjunction feeder; // only for part 2
    private Map<String, Long> feederInputWhenHigh; // only for part 2

    public long pressButton(long times) {
        for (long i = 0; i < times; i++) {
            pressButton();
        }

        LOGGER.info("LOWs {}", lows);
        LOGGER.info("HIGHs {}", highs);

        long totalLow = lows.values().stream().mapToLong(l -> l).sum();
        long totalHigh = highs.values().stream().mapToLong(l -> l).sum();

        LOGGER.info("total LOW {}", totalLow);
        LOGGER.info("total HIGH {}", totalHigh);

        return totalLow * totalHigh;
    }

    public long solvePart2() {
        feederInputWhenHigh = feeder.upstreamsWhithLastPulse.keySet()
                .stream().collect(Collectors.toMap(Function.identity(), a -> 0L));

        for (long i = 0; i < 10_000; i++) {
            pressButton();
            if (feederInputWhenHigh.values().stream().allMatch(x -> x > 0)) {
                break;
            }
        }
        return feederInputWhenHigh.values().stream()
                .mapToLong(l -> l).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public void pressButton() {
        currentRun++;
        LOGGER.debug("Current run {}", currentRun);
        send(new Signal("button", LOW, "broadcaster"));

        while (!queue.isEmpty()) {
            Signal s = queue.pop();
            LOGGER.debug("Signal: {}", s);
            if (feederInputWhenHigh != null && StringUtils.equals(s.to, feeder.name) &&
                    feederInputWhenHigh.get(s.from) == 0L && s.p == HIGH) {
                feederInputWhenHigh.put(s.from, currentRun);
            }
            modules.get(s.to).receiveSignal(s);
        }
    }

    public void send(Signal signal) {
        switch (signal.p) {
            case LOW -> lows.merge(currentRun, 1L, Long::sum);
            case HIGH -> highs.merge(currentRun, 1L, Long::sum);
        }
        queue.add(signal);
    }

    public void parseInput(List<String> input) {

        // create modules
        for (String s : input) {
            String[] split = s.split(" -> ");

            String sender = split[0];
            M m;
            if (sender.equals("broadcaster")) {
                m = new Broadcaster();
            } else if (sender.startsWith("%")) {
                m = new FlipFlop(sender.substring(1));
            } else if (sender.startsWith("&")) {
                m = new Conjunction(sender.substring(1));
            } else {
                throw new IllegalArgumentException("Unexpected module type: " + sender);
            }
            modules.put(m.getName(), m);
        }
        LOGGER.info("Modules (still to connect): {}", modules);

        // connect modules
        for (String s : input) {
            String[] split = s.split(" -> ");
            String sender = split[0].replace("%", "").replace("&", "");
            M m = modules.get(sender);

            for (String r : split[1].split(", ")) {
                M receiver = modules.computeIfAbsent(r, NoOp::new);
                m.downstream(receiver);
                receiver.upstream(m.getName());

                if (StringUtils.equals("rx", receiver.getName())) {
                    // this is the output module for part 2
                    assert m instanceof Conjunction; // expected to be a Conjunction
                    feeder = (Conjunction) m;
                }
            }
        }
        LOGGER.info("Modules (connected): {}", modules);
    }


    public abstract class M {
        protected final List<String> downstreams = new ArrayList<>();

        public abstract String getName();

        public void upstream(String name) {
            // do nothing, only Conjuctions has a special logic
        }

        public void downstream(M m) {
            downstreams.add(m.getName());
        }

        public abstract void receiveSignal(Signal p);

    }

    public record Signal(String from, Pulse p, String to) {
        @Override
        public String toString() {
            return "%s -%s-> %s".formatted(from, p, to);
        }
    }

    public enum Pulse {
        LOW, HIGH
    }

    public class Broadcaster extends M {

        @Override
        public String getName() {
            return "broadcaster";
        }

        @Override
        public void receiveSignal(Signal s) {
            for (String downstream : downstreams) {
                send(new Signal(this.getName(), s.p, downstream));
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Broadcaster.class.getSimpleName() + "[", "]")
                    .add("downstreams=" + downstreams)
                    .toString();
        }
    }

    public class FlipFlop extends M {
        private final String name;
        private boolean on = false;

        public FlipFlop(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void receiveSignal(Signal s) {
            if (s.p == HIGH) return;

            on = !on;

            Pulse pulse = on ? HIGH : LOW;

            for (String downstream : downstreams) {
                send(new Signal(this.getName(), pulse, downstream));
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", FlipFlop.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("downstreams=" + downstreams)
                    .add("on=" + on)
                    .toString();
        }
    }

    public class Conjunction extends M {
        private final String name;
        private final Map<String, Pulse> upstreamsWhithLastPulse = new LinkedHashMap();

        public Conjunction(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void upstream(String name) {
            upstreamsWhithLastPulse.put(name, LOW);

        }

        @Override
        public void receiveSignal(Signal s) {
            upstreamsWhithLastPulse.put(s.from, s.p);
            boolean allHigh = upstreamsWhithLastPulse.values().stream().allMatch(p -> p == HIGH);
            Pulse pulse = allHigh ? LOW : HIGH;

            for (String downstream : downstreams) {
                send(new Signal(this.getName(), pulse, downstream));
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Conjunction.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("downstreams=" + downstreams)
                    .add("upstreamsWhithLastPulse=" + upstreamsWhithLastPulse)
                    .toString();
        }
    }

    private class NoOp extends M {
        private final String name;

        public NoOp(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void receiveSignal(Signal p) {
            // do nothing
        }
    }

}
