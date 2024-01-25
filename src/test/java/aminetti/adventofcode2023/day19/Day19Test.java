package aminetti.adventofcode2023.day19;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day19Test {

    @Test
    void testInput() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_test_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solve();

        assertThat(l, is(19114L));
    }

    @Test
    void testActualInput() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solve();

        assertThat(l, is(449531L));
    }


}