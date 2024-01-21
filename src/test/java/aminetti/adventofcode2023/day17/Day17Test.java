package aminetti.adventofcode2023.day17;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day17Test {

    @Test
    void testInput() {
        List<String> list = readLines(Day17Test.class.getResourceAsStream("/day17/day17_test_input.txt"), UTF_8);

        Day17 solver = new Day17(list);
        long l = solver.solve();

        assertThat(l, is(102L));
    }
    @Test
    void testActualInput() {
        List<String> list = readLines(Day17Test.class.getResourceAsStream("/day17/day17_input.txt"), UTF_8);

        Day17 solver = new Day17(list);
        long l = solver.solve();

        assertThat(l, is(1244L));
    }


    @Test
    void testInputPart2() {
        List<String> list = readLines(Day17Test.class.getResourceAsStream("/day17/day17_test_input.txt"), UTF_8);

        Day17 solver = new Day17(list);
        long l = solver.solvePart2();

        assertThat(l, is(94L));
    }
    @Test
    void testActualInputPart2() {
        List<String> list = readLines(Day17Test.class.getResourceAsStream("/day17/day17_input.txt"), UTF_8);

        Day17 solver = new Day17(list);
        long l = solver.solvePart2();

        assertThat(l, is(1367L));
    }
}