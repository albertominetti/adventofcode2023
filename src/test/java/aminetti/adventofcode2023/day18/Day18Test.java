package aminetti.adventofcode2023.day18;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

class Day18Test {


    @Test
    void testInput() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_test_input.txt"), UTF_8);

        Day18 solver = new Day18(list);
        long l = solver.solveWithGrid();

        assertThat(l, is(62L));
    }

    @Test
    void testActualInput() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_input.txt"), UTF_8);

        Day18 solver = new Day18(list);
        long l = solver.solveWithGrid();

        assertThat(l, is(greaterThan(76311L)));
        assertThat(l, is(76387L));
    }


    @Test
    void testInputPart1WithPick() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_test_input.txt"), UTF_8);

        Day18 solver = new Day18(list);
        long l = solver.solveWithPick();

        assertThat(l, is(62L));
    }

    @Test
    void testActualInputPart1WithPick() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_input.txt"), UTF_8);

        Day18 solver = new Day18(list);
        long l = solver.solveWithPick();

        assertThat(l, is(76387L));
    }


    @Test
    void testInputPart2() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_test_input.txt"), UTF_8);

        Day18 solver = new Day18(list, true);
        long l = solver.solveWithPick();

        assertThat(l, is(952408144115L));
    }

    @Test
    void testActualInputPart2() {
        List<String> list = readLines(Day18Test.class.getResourceAsStream("/day18/day18_input.txt"), UTF_8);

        Day18 solver = new Day18(list, true);
        long l = solver.solveWithPick();

        assertThat(l, is(250022188522074L));
    }

}