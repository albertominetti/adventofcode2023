package aminetti.adventofcode2023.day06;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Long.parseLong;

public class WaitForIt {


    public List<Long> readNumbers(String line) {
        List<Long> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            results.add(parseLong(matcher.group()));
        }
        return results;
    }

    public long solve(List<String> lines) {
        List<Long> times = readNumbers(lines.get(0));
        List<Long> distances = readNumbers(lines.get(1));

        return IntStream.range(0, times.size())
                .unordered().parallel()
                .mapToLong(i -> waysToWin(times.get(i), distances.get(i)))
                .peek(x -> System.out.println(x))
                .reduce(1, (a, b) -> a * b);

    }

    public long waysToWin(Long totalTime, Long recordDistance) {
        return maxHoldingToWin(totalTime, recordDistance) - minHoldingToWin(totalTime, recordDistance) + 1;
    }

    public long maxHoldingToWin(Long totalTime, Long recordDistance) {
        for (long timeHolding = totalTime; timeHolding > 0; timeHolding--) {
            long totalDistance = (totalTime - timeHolding) * timeHolding;
            if (totalDistance > recordDistance) {
                return timeHolding;
            }
        }
        throw new RuntimeException("Can't win");
    }

    public long minHoldingToWin(Long totalTime, Long recordDistance) {
        for (long timeHolding = 0; timeHolding < totalTime; timeHolding++) {
            long totalDistance = (totalTime - timeHolding) * timeHolding;
            if (totalDistance > recordDistance) {
                return timeHolding;
            }
        }
        throw new RuntimeException("Can't win");
    }
}
