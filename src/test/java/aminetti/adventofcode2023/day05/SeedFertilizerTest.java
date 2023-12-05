package aminetti.adventofcode2023.day05;

import org.junit.jupiter.api.Test;

import java.util.List;

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

        List<Integer> seeds = seedFertilizer.readSeeds(list);

        assertThat(seeds, hasSize(4));
        assertThat(seeds, containsInAnyOrder(79, 14, 55, 13));
    }

    @Test
    void testMapper() {

        SeedFertilizer.Mapper mapper = new SeedFertilizer.Mapper("test");

        mapper.add(100, 200, 20);
        mapper.add(10, 4, 50);

        assertThat(mapper.map(90), is(90));
        assertThat(mapper.map(110), is(210));
        assertThat(mapper.map(12), is(6));
        assertThat(mapper.map(300), is(300));
    }

    @Test
    void solve() {
        List<String> list = readLines(SeedFertilizerTest.class.getResourceAsStream("/day05/day5_test_input.txt"), UTF_8);

        int min = seedFertilizer.solvePart1(list);

        assertThat(min, is(35));
    }
}