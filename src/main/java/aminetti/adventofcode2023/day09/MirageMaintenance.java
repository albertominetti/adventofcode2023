package aminetti.adventofcode2023.day09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static aminetti.adventofcode2023.ParsingUtils.readNumbers;

public class MirageMaintenance {
    private static final Logger LOGGER = LoggerFactory.getLogger(MirageMaintenance.class);

    public char[] getMoves(String movesInList) {
        return movesInList.toCharArray();
    }

    public long solve(List<String> lines) {
        List<Long> forecasts = new ArrayList<>(lines.size());
        for (String line : lines) {
            List<Long> inputLine = readNumbers(line);
            LOGGER.info("Calculating forecast for line {}", inputLine);

            forecasts.add(forecastLastOne(inputLine));
        }

        return forecasts.stream().mapToLong(z -> z).sum();
    }

    public long solve2(List<String> lines) {
        List<Long> forecasts = new ArrayList<>(lines.size());
        for (String line : lines) {
            List<Long> inputLine = readNumbers(line);
            LOGGER.info("Calculating forecast for line {}", inputLine);

            forecasts.add(forecastFirst(inputLine));
        }

        return forecasts.stream().mapToLong(z -> z).sum();
    }

    public long forecastLastOne(List<Long> inputLine) {
        if (inputLine.stream().allMatch(x -> x == 0)) {
            LOGGER.info("Line {} forecast (base) {}", inputLine, 0);
            return 0;
        }

        List<Long> deltas = new ArrayList<>(inputLine.size() - 1);
        for (int i = 0; i < inputLine.size() - 1; i++) {
            long delta = inputLine.get(i + 1) - inputLine.get(i);
            deltas.add(delta);
        }

        long forecast = forecastLastOne(deltas);

        long l = inputLine.get(inputLine.size() - 1) + forecast;
        LOGGER.info("Line {} forecast {}", inputLine, l);

        return l;

    }

    public long forecastFirst(List<Long> inputLine) {
        if (inputLine.stream().allMatch(x -> x == 0)) {
            LOGGER.info("Line {} precast (base) {}", inputLine, 0);
            return 0;
        }

        List<Long> deltas = new ArrayList<>(inputLine.size() - 1);
        for (int i = 0; i < inputLine.size() - 1; i++) {
            long delta = inputLine.get(i + 1) - inputLine.get(i);
            deltas.add(delta);
        }

        long forecast = forecastFirst(deltas);

        long l = inputLine.get(0) - forecast;
        LOGGER.info("Line {} precast {}", inputLine, l);

        return l;

    }


}
