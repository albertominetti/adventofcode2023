package aminetti.adventofcode2023.day08;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
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


    @Test
    void solveTestPart2() {

        List<String> testInput = List.of(
                "LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)"
        );

        long solve = solver.solvePart2MovingSynchnonouslyInThePaths(testInput);

        assertThat(solve, is(6L));


    }

    @Test
    void solveTestPart2Bis() {

        List<String> testInput = List.of(
                "LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)"
        );

        BigInteger solve = solver.solvePart2UsingTheLeastCommonMultiple(testInput);

        assertThat(solve, is(new BigInteger("6")));


    }

    @Test
    void solvePart2() {
        List<String> list = readLines(HauntedWastelandTest.class.getResourceAsStream("/day08/day8_input.txt"), UTF_8);

        BigInteger solve = solver.solvePart2UsingTheLeastCommonMultiple(list);

        assertThat(solve, greaterThan(new BigInteger("1817704797")));

        System.out.println(solve);

    }
}