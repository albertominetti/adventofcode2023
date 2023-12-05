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

    public List<Mapper> readMappers(List<String> lines) {
        List<Mapper> mappers = new ArrayList<>();

        LOGGER.info("Lists: {}", lines);

        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            Matcher nameMatcher = MAPPER_NAME.matcher(line);
            if (!nameMatcher.matches()) {
                throw new IllegalArgumentException("Can't check the name, probably wrong input");
            }
            Mapper current = new Mapper(nameMatcher.group(1));

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

    public List<Integer> mapSeeds(List<Mapper> mappers, List<Integer> seeds) {
        List<Integer> results = new ArrayList<>();
        for (Integer seed : seeds) {
            int current = seed;
            for (Mapper mapper : mappers) {
                current = mapper.map(current);
            }
            results.add(current);
        }
        return results;
    }

    public int solvePart1(List<String> lines) {
        List<Integer> seeds = readSeeds(lines);
        List<SeedFertilizer.Mapper> mappers = readMappers(lines.subList(2, lines.size()));

        List<Integer> localtions = mapSeeds(mappers, seeds);

        return localtions.stream().min(Integer::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Something messy with the input?"));

    }

    public static class Mapper {
        private final String name;
        private final SortedSet<MappingRule> rules = new TreeSet<>();

        public Mapper(String name) {
            this.name = name;
        }

        public int map(int current) {
            for (MappingRule element : rules) {
                if (current < element.source) break;
                if (current < element.source + element.range) {
                    return current + (element.destination - element.source);
                }
            }
            return current;
        }

        public void add(int source, int destination, int range) {
            rules.add(new MappingRule(source, destination, range));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Mapper.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("fromToRangeList=" + rules)
                    .toString();
        }

        public record MappingRule(int source, int destination, int range) implements Comparable<MappingRule> {

            @Override
            public int compareTo(MappingRule o) {
                return Integer.compare(source, o.source);
            }
        }
    }

}
