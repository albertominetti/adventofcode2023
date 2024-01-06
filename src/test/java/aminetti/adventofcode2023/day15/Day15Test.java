package aminetti.adventofcode2023.day15;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day15Test {

    String sampleInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    @Test
    void testSampleInput() {
        long l = new Day15().sumOfHashes(sampleInput);

        assertThat(l, is(1320L));
    }

    @Test
    void testActualInput() {
        List<String> list = readLines(Day15Test.class.getResourceAsStream("/day15/day15_input.txt"), UTF_8);

        Day15 solver = new Day15();

        long l = solver.sumOfHashes(list.getFirst());

        assertThat(l, is(517315L));


    }

    @Test
    void testPart2SampleInput() {
        long l = new Day15().solveBoxes(sampleInput);

        assertThat(l, is(145L));
    }


    @Test
    void testPart2ActualInput() {
        List<String> list = readLines(Day15Test.class.getResourceAsStream("/day15/day15_input.txt"), UTF_8);

        Day15 solver = new Day15();

        long l = solver.solveBoxes(list.getFirst());

        assertThat(l, is(247763L));


    }

}