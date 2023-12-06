package aminetti.adventofcode2023.day05;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SeedFertilizerTest {

    private final SeedFertilizer seedFertilizer = new SeedFertilizer();

    @Test
    void readMappers() {
        List<String> list = readLines(SeedFertilizerTest.class.getResourceAsStream("/day05/day5_test_input.txt"), UTF_8);

        List<SeedFertilizer.Mapper> mappers = seedFertilizer.readMappers(list.subList(2, list.size()));
        System.out.println(mappers);

        assertThat(true, is(true));

    }

    @Test
    void readSeeds() {
        List<String> list = readLines(SeedFertilizerTest.class.getResourceAsStream("/day05/day5_test_input.txt"), UTF_8);

        List<Long> seeds = seedFertilizer.readSeeds(list);

        assertThat(seeds, hasSize(4));
        assertThat(seeds, containsInAnyOrder(79L, 14L, 55L, 13L));
    }

    @Test
    void testMapper() {

        SeedFertilizer.Mapper mapper = new SeedFertilizer.Mapper("test");

        mapper.add(100, 200, 20);
        mapper.add(10, 4, 50);

        assertThat(mapper.map(90), is(90L));
        assertThat(mapper.map(110), is(210L));
        assertThat(mapper.map(12), is(6L));
        assertThat(mapper.map(300), is(300L));
    }

    @Test
    void solve() {
        List<String> list = readLines(SeedFertilizerTest.class.getResourceAsStream("/day05/day5_test_input.txt"), UTF_8);

        long min = seedFertilizer.solvePart1(list);

        assertThat(min, is(35L));
    }

    @Test
    void testForStreamConcatenation() {
        Stream<String> x = Stream.empty();

        for (int i = 0; i < 5; i++) {
            x = Stream.concat(x, Stream.of("" + 1, "asd" + i));
        }

        x.unordered().parallel().forEach(System.out::println);
    }

    @Test
    @Disabled("Slow! It takes 3 minutes to complete")
    void solvePart2() {
        List<String> list = readLines(SeedFertilizerTest.class.getResourceAsStream("/day05/day5_input.txt"), UTF_8);

        long min = seedFertilizer.solvePart2(list);

        assertThat(min, is(10834440L));
    }
}