package aminetti.adventofcode2023.day15;

import java.util.Arrays;

public class Day15 {

    public long sumOfHashes(String line) {
        return Arrays.stream(line.split(","))
                .mapToLong(s -> s.chars()
                        .reduce(0, (a, b) -> ((a + b) * 17) % 256)
                ).sum();
    }

}
