package aminetti.adventofcode2023.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class Day14Test {

    @Test
    void test() {
        List<String> list = readLines(Day14Test.class.getResourceAsStream("/day14/day14_test_input.txt"), UTF_8);

        Day14 solver = new Day14(list);

        long weight = solver.solve();

        assertThat(weight, is(136L));
    }

    @Test
    void testActualInput() {
        List<String> list = readLines(Day14Test.class.getResourceAsStream("/day14/day14_input.txt"), UTF_8);

        Day14 solver = new Day14(list);

        long weight = solver.solve();

        assertThat(weight, is(108955L));


    }

    @Test
    void testPart2() {
        List<String> list = readLines(Day14Test.class.getResourceAsStream("/day14/day14_test_input.txt"), UTF_8);

        Day14 solver = new Day14(list);

        long weight = solver.solvePart2();

        assertThat(weight, is(64L));
    }

    @Test
    void testPart2ActualInput() {
        List<String> list = readLines(Day14Test.class.getResourceAsStream("/day14/day14_input.txt"), UTF_8);

        Day14 solver = new Day14(list);

        long weight = solver.solvePart2();

        assertThat(weight, is(greaterThan(64L)));
        assertThat(weight, is(lessThan(106699L)));
        assertThat(weight, is(lessThan(106708L)));
        assertThat(weight, is(64L));
    }

}