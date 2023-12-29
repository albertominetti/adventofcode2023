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
        Day11 solver = new Day11(testInput, 2);
        long solve = solver.solve();

        assertThat(solve, is(374L));
    }

    @Test
    void solvePart2TestData() {
        Day11 solver = new Day11(testInput, 10);
        long solve = solver.solve();

        assertThat(solve, is(1030L));
    }

    @Test
    void solvePart2TestDataBis() {
        Day11 solver = new Day11(testInput, 100);
        long solve = solver.solve();

        assertThat(solve, is(8410L));
    }


    @Test
    void solvePart1() {
        List<String> list = readLines(Day11Test.class.getResourceAsStream("/day11/day11_input.txt"), UTF_8);
        Day11 solver = new Day11(list, 2);
        long solve = solver.solve();

        assertThat(solve, is(9681886L));
    }

    @Test
    void solvePart2() {
        List<String> list = readLines(Day11Test.class.getResourceAsStream("/day11/day11_input.txt"), UTF_8);
        Day11 solver = new Day11(list, 1000000);
        long solve = solver.solve();

        assertThat(solve, is(791134099634L));
    }


}