package aminetti.adventofcode2023.day06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static aminetti.adventofcode2023.ParsingUtils.readNumbers;
import static java.lang.Long.parseLong;

public class WaitForIt {
    private static final Logger LOGGER = LoggerFactory.getLogger(WaitForIt.class);


    public Long readNumberWithBadKerning(String line) {
        Pattern pattern = Pattern.compile("[^\\d]");

        Matcher matcher = pattern.matcher(line);
        String onlyNumbers = matcher.replaceAll("");

        return parseLong(onlyNumbers);
    }

    public long solvePart2(List<String> lines) {
        Long time = readNumberWithBadKerning(lines.get(0));
        Long distance = readNumberWithBadKerning(lines.get(1));

        LOGGER.info("Race time {} and record distance {}", time, distance);

        return waysToWin(time, distance);

    }

    public long solve(List<String> lines) {
        List<Long> times = readNumbers(lines.get(0));
        List<Long> distances = readNumbers(lines.get(1));

        return IntStream.range(0, times.size())
                .unordered().parallel()
                .mapToLong(i -> waysToWin(times.get(i), distances.get(i)))
                .peek(x -> LOGGER.info("Ways to win: {}", x))
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
