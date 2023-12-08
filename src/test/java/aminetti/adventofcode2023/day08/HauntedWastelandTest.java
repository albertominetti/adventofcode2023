package aminetti.adventofcode2023.day08;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

class HauntedWastelandTest {
    HauntedWasteland solver = new HauntedWasteland();

    List<String> testInput = List.of(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)"
    );

    @Test
    void getMoves() {
        char[] moves = solver.getMoves(testInput.get(0));

        System.out.println(moves);
    }

    @Test
    void readMap() {
        Map<String, HauntedWasteland.LeftRight> stringLeftRightMap = solver.readMap(testInput.subList(2, testInput.size()));

        System.out.println(stringLeftRightMap);

    }

    @Test
    void solve() {
        long solve = solver.solve(testInput);

        assertThat(solve, is(2L));
    }

    @Test
    void solveRealInput() {
        List<String> list = readLines(HauntedWastelandTest.class.getResourceAsStream("/day08/day8_input.txt"), UTF_8);

        long solve = solver.solve(list);

        assertThat(solve, greaterThan(271L));
        assertThat(solve, is(18157L));


    }
}