package aminetti.adventofcode2023.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day11Test {

    List<String> testInput = List.of(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#....."
    );

    @Test
    void solvePart1TestData() {
        Day11 solver = new Day11(testInput);
        long solve = solver.solvePart1();

        assertThat(solve, is(374));
    }

    @Test
    void solvePart1() {
        List<String> list = readLines(Day11Test.class.getResourceAsStream("/day11/day11_input.txt"), UTF_8);
        Day11 solver = new Day11(list);
        long solve = solver.solvePart1();

        assertThat(solve, is(2L));
    }

    @Test
    void solvePart2() {
        List<String> list = readLines(Day11Test.class.getResourceAsStream("/day11/day11_input.txt"), UTF_8);

//        long solve = solver.solve(list);
//
//        assertThat(solve, is(2L));

    }

}