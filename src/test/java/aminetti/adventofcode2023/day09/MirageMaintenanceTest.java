package aminetti.adventofcode2023.day09;

import org.junit.jupiter.api.Test;

import java.util.List;

import static aminetti.adventofcode2023.ParsingUtils.readNumbers;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

class MirageMaintenanceTest {
    MirageMaintenance solver = new MirageMaintenance();

    List<String> testInput = List.of(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45"
    );


    @Test
    void solve() {
        long solve = solver.solve(testInput);

        assertThat(solve, is(114L));
    }

    @Test
    void forecast() {
        long solve = solver.forecastLastOne(readNumbers("0 3 6 9 12 15"));

        assertThat(solve, is(18L));
    }

    @Test
    void solvePart1() {
        List<String> list = readLines(MirageMaintenanceTest.class.getResourceAsStream("/day09/day9_input.txt"), UTF_8);

        long solve = solver.solve(list);

        assertThat(solve, lessThan(2013149021L));
        assertThat(solve, lessThan(1974232257L));
        assertThat(solve,       is(1974232257L));

    }

    @Test
    void solvePart2() {
        List<String> list = readLines(MirageMaintenanceTest.class.getResourceAsStream("/day09/day9_input.txt"), UTF_8);

        long solve = solver.solve2(list);

        assertThat(solve,       is(928L));

    }

}