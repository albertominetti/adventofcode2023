package aminetti.adventofcode2023.day19;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static aminetti.adventofcode2023.day19.Day19.RangePart.sumFirstNums;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day19Test {

    @Test
    void testInput() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_test_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solve();

        assertThat(l, is(19114L));
    }

    @Test
    void actualInput() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solve();

        assertThat(l, is(449531L));
    }

    @Test
    void testSumWithRange() {
        Day19.RangePart rangePart = new Day19.RangePart("test");
        rangePart.max = new HashMap<>();
        rangePart.min = new HashMap<>();

        rangePart.min.put("x", 1L);
        rangePart.min.put("a", 1L);
        rangePart.min.put("m", 1L);
        rangePart.min.put("s", 1L);

        rangePart.max.put("x", 1L);
        rangePart.max.put("a", 1L);
        rangePart.max.put("m", 1L);
        rangePart.max.put("s", 1L);

        assertThat(rangePart.totalCombinations(), is(1L));
        assertThat(rangePart.totalSumOfCombinationsWithMath(), is(4L));
    }

    @Test
    void testSumWithRange2() {
        Day19.RangePart rangePart = new Day19.RangePart("test");
        rangePart.max = new HashMap<>();
        rangePart.min = new HashMap<>();

        rangePart.min.put("x", 1L);
        rangePart.min.put("a", 1L);
        rangePart.min.put("m", 1L);
        rangePart.min.put("s", 1L);

        rangePart.max.put("x", 1L);
        rangePart.max.put("a", 2L);
        rangePart.max.put("m", 1L);
        rangePart.max.put("s", 1L);

        assertThat(rangePart.totalCombinations(), is(2L));
        assertThat(rangePart.totalSumOfCombinationsWithMath(), is(9L));
    }

    @Test
    void testSumWithRange3() {
        Day19.RangePart rangePart = new Day19.RangePart("test");
        rangePart.max = new HashMap<>();
        rangePart.min = new HashMap<>();

        rangePart.min.put("x", 1L);
        rangePart.min.put("a", 1L);
        rangePart.min.put("m", 1L);
        rangePart.min.put("s", 1L);

        rangePart.max.put("x", 5L);
        rangePart.max.put("a", 2L);
        rangePart.max.put("m", 1L);
        rangePart.max.put("s", 1L);

        assertThat(rangePart.totalCombinations(), is(10L));
        long tot = 0;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 2; j++) {
                tot += i + j + 1 + 1;
            }
        }
        assertThat(rangePart.totalSumOfCombinationsWithMath(), is(tot));
    }


    @Test
    void testSumWithRange4() {
        Day19.RangePart rangePart = new Day19.RangePart("test");
        rangePart.max = new HashMap<>();
        rangePart.min = new HashMap<>();

        rangePart.min.put("x", 1L);
        rangePart.min.put("a", 100L);
        rangePart.min.put("m", 1L);
        rangePart.min.put("s", 1L);

        rangePart.max.put("x", 5L);
        rangePart.max.put("a", 120L);
        rangePart.max.put("m", 1L);
        rangePart.max.put("s", 1L);

        assertThat(rangePart.totalCombinations(), is((long) 21 * 5));
        long tot = 0;
        for (int i = 1; i <= 5; i++) {
            for (int j = 100; j <= 120; j++) {
                tot += i + j + 1 + 1;
            }
        }
        System.out.println(tot);
        assertThat(rangePart.totalSumOfCombinationsWithMath(), is(tot));
    }

    @Test
    void mathTest() {
        assertThat(sumFirstNums(5), is((long) 1 + 2 + 3 + 4 + 5));
        assertThat(sumFirstNums(3), is((long) 1 + 2 + 3));

        long sumDiff = sumFirstNums(5) - sumFirstNums(3);
        long result = IntStream.rangeClosed(4, 5).sum();
        assertThat(sumDiff, is((long) 1 + 2 + 3 + 4 + 5 - (1 + 2 + 3)));
        assertThat(sumDiff, is(result));
    }

    @Test
    void testInputPart2() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_test_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solveWithMetaParts();

        assertThat(l, is(167409079868000L));
    }

    @Test
    void actualInputPart2() {
        List<String> list = readLines(Day19Test.class.getResourceAsStream("/day19/day19_input.txt"), UTF_8);

        Day19 solver = new Day19(list);
        long l = solver.solveWithMetaParts();

        assertThat(l, is(122756210763577L));
    }


}