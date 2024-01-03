package aminetti.adventofcode2023.day13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static aminetti.adventofcode2023.day13.Day13.calc;
import static aminetti.adventofcode2023.day13.Day13.readInput;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class Day13Test {
    Day13 solver;

    @Test
    void test() {
        List<String> list = readLines(Day13Test.class.getResourceAsStream("/day13/day13_test_input.txt"), UTF_8);
        System.out.println(list);
        System.out.println(readInput(list));

    }

    @Test
    void test2() {
        List<String> input = readLines(Day13Test.class.getResourceAsStream("/day13/day13_test_input.txt"), UTF_8);
        List<List<String>> lists = readInput(input);
        long note = calc(lists.get(0)) + calc(lists.get(1));

        assertThat(note, is(405L));

    }

    @Test
    void solveActualInput() {
        List<String> list = readLines(Day13Test.class.getResourceAsStream("/day13/day13_input.txt"), UTF_8);
        Day13 solver = new Day13(list);
        long totalNotes = solver.solve();

        assertThat(totalNotes, is(27502L));

    }

}