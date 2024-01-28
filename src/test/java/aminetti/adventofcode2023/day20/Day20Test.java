package aminetti.adventofcode2023.day20;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day20Test {

    @Test
    void testInput() {
        List<String> list = readLines(Day20Test.class.getResourceAsStream("/day20/day20_test_input.txt"), UTF_8);

        Day20 solver = new Day20();
        solver.parseInput(list);
        long l = solver.pressButton(1000);

        assertThat(l, is(32000000L));

    }

    @Test
    void testInput2() {
        List<String> list = readLines(Day20Test.class.getResourceAsStream("/day20/day20_test_input2.txt"), UTF_8);

        Day20 solver = new Day20();
        solver.parseInput(list);
        long l = solver.pressButton(1000);

        assertThat(l, is(11687500L));

    }

    @Test
    void actualInput() {
        List<String> list = readLines(Day20Test.class.getResourceAsStream("/day20/day20_input.txt"), UTF_8);

        Day20 solver = new Day20();
        solver.parseInput(list);
        long l = solver.pressButton(1000);

        assertThat(l, is(899848294L));

    }

    @Test
    void actualInputPart2() {
        List<String> list = readLines(Day20Test.class.getResourceAsStream("/day20/day20_input.txt"), UTF_8);

        Day20 solver = new Day20();
        solver.parseInput(list);
        long l = solver.solvePart2();

        assertThat(l, is(247454898168563L));

    }

}