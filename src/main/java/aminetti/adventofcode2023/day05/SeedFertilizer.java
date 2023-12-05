package aminetti.adventofcode2023.day05;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class SeedFertilizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedFertilizer.class);

    private static final Pattern DEST_SOURCE_RANGE = Pattern.compile("(\\d+) (\\d+) (\\d+)");
    private static final Pattern MAPPER_NAME = Pattern.compile("(.+) map:");

    public List<ConsecutiveMapper> readMappers(List<String> lines) {
        List<ConsecutiveMapper> mappers = new ArrayList<>();

        LOGGER.info("Lists: {}", lines);

        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            Matcher nameMatcher = MAPPER_NAME.matcher(line);
            if (!nameMatcher.matches()) {
                throw new IllegalArgumentException("Can't check the name, probably wrong input");
            }
            ConsecutiveMapper current = new ConsecutiveMapper(nameMatcher.group(1));

            line = iterator.next();

            while (!StringUtils.isBlank(line) && iterator.hasNext()) {
                Matcher matcher = DEST_SOURCE_RANGE.matcher(line);
                if (matcher.matches()) {
                    int destinationStart = parseInt(matcher.group(1));
                    int sourceStart = parseInt(matcher.group(2));
                    int rangeLength = parseInt(matcher.group(3));

                    current.add(sourceStart, destinationStart, rangeLength);
                }
                line = iterator.next();
            }

            mappers.add(current);

        }


        return mappers;
    }

    public List<Integer> readSeeds(List<String> lines) {
        List<Integer> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcher = pattern.matcher(lines.get(0)); // getFirst() should be available

        while (matcher.find()) {
            results.add(parseInt(matcher.group()));
        }
        return results;
    }

    public List<Integer> solve(List<ConsecutiveMapper> mappers, List<Integer> seeds) {
        List<Integer> results = new ArrayList<>();
        for (Integer seed : seeds) {
            int current = seed;
            for (ConsecutiveMapper mapper : mappers) {
                current = mapper.map(current);
            }
            results.add(current);
        }
        return results;
    }

    public static class ConsecutiveMapper {
        private final String name;
        private final SortedSet<SourceDestinationRange> sourceDestinationRangeList = new TreeSet<>();

        public ConsecutiveMapper(String name) {
            this.name = name;
        }

        public int map(int current) {

            Iterator<SourceDestinationRange> iterator = sourceDestinationRangeList.iterator();
            while (iterator.hasNext()) {
                SourceDestinationRange element = iterator.next();

                if (element.source <= current && current < element.source + element.range) {
                    // to be completed....
                }

            }
            return current;
        }

        public void add(int source, int destination, int range) {
            sourceDestinationRangeList.add(new SourceDestinationRange(source, destination, range));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", ConsecutiveMapper.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("fromToRangeList=" + sourceDestinationRangeList)
                    .toString();
        }

        public record SourceDestinationRange(int source, int destination, int range) implements Comparable<SourceDestinationRange> {

            @Override
            public int compareTo(SourceDestinationRange o) {
                return Integer.compare(source, o.source);
            }
        }
    }

}
