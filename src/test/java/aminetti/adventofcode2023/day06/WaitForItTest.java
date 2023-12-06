package aminetti.adventofcode2023.day06;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class WaitForItTest {
    WaitForIt solver = new WaitForIt();

    @Test
    void solve() {
        long marginError = solver.solve(List.of(
                "Time:      7  15   30",
                "Distance:  9  40  200)"
        ));

        assertThat(marginError, is((long) 288));
    }

    @Test
    void waysToWin() {
        long min = solver.waysToWin(7L, 9L);

        assertThat(min, is((long) 5 - 2 + 1));
    }

    @Test
    void maxHoldingToWin() {
        long min = solver.maxHoldingToWin(7L, 9L);

        assertThat(min, is(5L));
    }

    @Test
    void minHoldingToWin() {
        long min = solver.minHoldingToWin(7L, 9L);

        assertThat(min, is(2L));
    }


    @Test
    void maxHoldingToWin2() {
        long min = solver.maxHoldingToWin(30L, 200L);

        assertThat(min, is(19L));
    }

    @Test
    void minHoldingToWin2() {
        long min = solver.minHoldingToWin(30L, 200L);

        assertThat(min, is(11L));
    }

    @Test
    void solvePart1() {
        List<String> list = readLines(WaitForItTest.class.getResourceAsStream("/day06/day6_input.txt"), UTF_8);

        long marginError = solver.solve(list);

        assertThat(marginError, is((long) 220320));
    }
}