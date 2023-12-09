package aminetti.adventofcode2023.day09;

import aminetti.adventofcode2023.ParsingUtils;
import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MirageMaintenance {
    private static final Logger LOGGER = LoggerFactory.getLogger(MirageMaintenance.class);


    public long solvePart1(List<String> lines) {
        return solve(lines, this::forecastFuture);
    }

    public long solvePart2(List<String> lines) {
        return solve(lines, this::predictHistorical);
    }


    private long solve(List<String> lines, Function<List<Long>, Long> forecastFunction) {
        return lines.stream().map(ParsingUtils::readNumbers)
                .peek(line -> LOGGER.info("Calculating forecast for line {}", line))
                .map(forecastFunction)
                .mapToLong(z -> z).sum();
    }

    long forecastFuture(List<Long> inputLine) {
        if (inputLine.stream().allMatch(x -> x == 0)) {
            LOGGER.info("Line {} forecasted (base case) {}", inputLine, 0);
            return 0;
        }

        long forecasted = forecastFuture(calculateDeltas(inputLine));

        long l = inputLine.get(inputLine.size() - 1) + forecasted;
        LOGGER.info("Line {} forecasted {}", inputLine, l);

        return l;
    }


    long predictHistorical(List<Long> inputLine) {
        if (inputLine.stream().allMatch(x -> x == 0)) {
            LOGGER.info("Line {} predicted (base case) {}", inputLine, 0);
            return 0;
        }

        long predicted = predictHistorical(calculateDeltas(inputLine));

        long l = inputLine.get(0) - predicted;
        LOGGER.info("Line {} predicted {}", inputLine, l);

        return l;
    }

    static List<Long> calculateDeltas(List<Long> line) {
        return Streams.zip(line.stream(), line.stream().skip(1), ImmutablePair::of)
                .peek(p -> LOGGER.trace("Pair: {}", p))
                .map(p -> p.getRight() - p.getLeft())
                .collect(Collectors.toList());
    }


}
