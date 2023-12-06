package aminetti.adventofcode2023.day05;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

import static java.lang.Long.parseLong;

public class SeedFertilizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeedFertilizer.class);

    private static final Pattern DEST_SOURCE_RANGE = Pattern.compile("(\\d+) (\\d+) (\\d+)");
    private static final Pattern MAPPER_NAME = Pattern.compile("(.+) map:");

    public List<Mapper> readMappers(List<String> lines) {
        List<Mapper> mappers = new ArrayList<>();

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
                    long destinationStart = parseLong(matcher.group(1));
                    long sourceStart = parseLong(matcher.group(2));
                    long rangeLength = parseLong(matcher.group(3));

                    current.add(sourceStart, destinationStart, rangeLength);
                }
                line = iterator.next();
            }

            mappers.add(current);

        }

        return mappers;
    }

    public List<Long> readSeeds(List<String> lines) {
        return readNumbers(lines.get(0));
    }

    public List<Long> readNumbers(String line) {
        List<Long> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            results.add(parseLong(matcher.group()));
        }
        return results;
    }

    public LongStream readSeedsAsRanges(List<String> lines) {
        LongStream resultStream = LongStream.empty();

        List<Long> results = readNumbers(lines.get(0));
        for (int i = 0; i < results.size(); i += 2) {
            Long from = results.get(i);
            Long range = results.get(i + 1);
            LOGGER.info("Preparing a Stream from {} to {}", from, from + range);
            resultStream = LongStream.concat(resultStream,
                    LongStream.range(from, from + range));
        }
        return resultStream;
    }

    public List<Long> mapSeeds(List<Mapper> mappers, List<Long> seeds) {
        List<Long> results = new ArrayList<>();
        for (Long seed : seeds) {
            long current = mapSeed(mappers, seed);
            results.add(current);
        }
        return results;
    }

    private static long mapSeed(List<Mapper> mappers, Long seed) {
        long current = seed;
        for (Mapper mapper : mappers) {
            current = mapper.map(current);
        }
        return current;
    }

    public long solvePart1(List<String> lines) {
        List<Long> seeds = readSeeds(lines);
        List<SeedFertilizer.Mapper> mappers = readMappers(lines.subList(2, lines.size()));

        List<Long> locations = mapSeeds(mappers, seeds);

        return locations.stream().min(Long::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("Something messy with the input?"));

    }

    public long solvePart2(List<String> lines) {
        LongStream seeds = readSeedsAsRanges(lines);
        List<SeedFertilizer.Mapper> mappers = readMappers(lines.subList(2, lines.size()));

        return seeds.unordered().parallel()
                .map(s -> mapSeed(mappers, s))
                .min()
                .orElseThrow(() -> new IllegalArgumentException("Something messy with the input?"));
    }

    public static class Mapper {
        private final String name;
        private final SortedSet<MappingRule> rules = new TreeSet<>();

        public Mapper(String name) {
            this.name = name;
        }

        public long map(long current) {
            for (MappingRule rule : rules) {
                if (current < rule.source) break;
                if (current < rule.source + rule.range) {
                    return current + (rule.destination - rule.source);
                }
            }
            return current;
        }

        public void add(long source, long destination, long range) {
            rules.add(new MappingRule(source, destination, range));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Mapper.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("fromToRangeList=" + rules)
                    .toString();
        }

        public record MappingRule(long source, long destination, long range) implements Comparable<MappingRule> {

            @Override
            public int compareTo(MappingRule o) {
                return Long.compare(source, o.source);
            }
        }
    }

}
