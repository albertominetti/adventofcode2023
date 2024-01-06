package aminetti.adventofcode2023.day16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

class Day16Test {

    List<String> input = List.of(".|...\\....",
            "|.-.\\.....",
            ".....|-...",
            "........|.",
            "..........",
            ".........\\",
            "..../.\\\\..",
            ".-.-/..|..",
            ".|....-|.\\",
            "..//.|....");

    @Test
    void testPart1Input() {
        long solve = new Day16(input).solve();

        assertThat(solve, is(46L));
    }

    @Test
    void testPart2Input() {
        long solve = new Day16(input).solvePart2();

        assertThat(solve, is(51L));
    }


    @Test
    void testPart1ActualInput() {
        List<String> list = readLines(Day16Test.class.getResourceAsStream("/day16/day16_input.txt"), UTF_8);

        Day16 solver = new Day16(list);
        long l = solver.solve();

        assertThat(l, is(8551L));
    }

    @Test
    void testPart2ActualInput() {
        List<String> list = readLines(Day16Test.class.getResourceAsStream("/day16/day16_input.txt"), UTF_8);

        Day16 solver = new Day16(list);
        long l = solver.solvePart2();

        assertThat(l, is(greaterThan(8695L)));
        assertThat(l, is(8754L));
    }

}