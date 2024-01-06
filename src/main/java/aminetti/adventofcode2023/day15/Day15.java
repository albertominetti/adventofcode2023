package aminetti.adventofcode2023.day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.IntStream;

public class Day15 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15.class);

    public long sumOfHashes(String line) {
        return Arrays.stream(line.split(","))
                .mapToLong(s -> hash(s)
                ).sum();
    }

    private static int hash(String s) {
        return s.chars().reduce(0, (a, b) -> ((a + b) * 17) % 256);
    }

    public long solveBoxes(String line) {
        String[] operations = line.split(",");

        HashMap<Integer, List<Lens>> boxes = new HashMap<>();
        IntStream.range(0, 256).forEach(i -> boxes.put(i, new LinkedList<>()));

        for (String operation : operations) {
            if (operation.contains("-")) {
                String label = operation.substring(0, operation.indexOf("-"));
                int boxIndex = hash(label);
                List<Lens> lenses = boxes.get(boxIndex);
                lenses.removeIf(l -> l.label.equals(label));
            } else if (operation.contains("=")) {

                int equalIndex = operation.indexOf("=");
                String label = operation.substring(0, equalIndex);
                int focalLength = Integer.parseInt(operation.substring(equalIndex + 1));

                int boxIndex = hash(label);
                List<Lens> lenses = boxes.get(boxIndex);
                Optional<Lens> optLens = lenses.stream().filter(l -> l.label.equals(label)).findAny();
                if (optLens.isPresent()) {
                    optLens.get().setFocalLength(focalLength);
                } else {
                    Lens newLens = new Lens(label, focalLength);
                    lenses.add(newLens);
                }
            }
        }

        LOGGER.info("Boxes: {}", boxes);

        return boxes.entrySet().stream().mapToLong(e -> (e.getKey() + 1) * lensPower(e.getValue())).sum();

    }

    private long lensPower(List<Lens> value) {
        long tot = 0;
        long lensCount = 1;
        for (Lens lens : value) {
            tot += lensCount * lens.focalLength;
            lensCount++;
        }
        return tot;
    }

    public static class Lens {
        private final String label;
        private int focalLength;

        public Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }

        public void setFocalLength(int focalLength) {
            this.focalLength = focalLength;
        }

        @Override
        public String toString() {
            return String.format("[%s %d]", label, focalLength);
        }
    }

}
