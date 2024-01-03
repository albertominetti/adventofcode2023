package aminetti.adventofcode2023.day12;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

class Day12Test {

    Day12 solver = new Day12(Collections.EMPTY_LIST);

    @Test
    void arrangements() {
        long arrangements = solver.calc("???.###", newArrayList(1, 1, 3));

        assertThat(arrangements, is(1L));
    }

    @Test
    void arrangements2() {
        long arrangements = solver.calc(".??..??...?##.", newArrayList(1, 1, 3));

        assertThat(arrangements, is(4L));
    }

    @Test
    void arrangements3() {
        long arrangements = solver.calc("?#?#?#?#?#?#?#?", newArrayList(1, 3, 1, 6));

        assertThat(arrangements, is(1L));
    }

    @Test
    void arrangements4() {
        long arrangements = solver.calc("????.#...#...", newArrayList(4, 1, 1));

        assertThat(arrangements, is(1L));
    }

    @Test
    void arrangements5() {
        long arrangements = solver.calc("????.######..#####.", newArrayList(1, 6, 5));

        assertThat(arrangements, is(4L));
    }

    @Test
    void arrangements6() {
        long arrangements = solver.calc("?###????????", newArrayList(3, 2, 1));

        assertThat(arrangements, is(10L));
    }

    @Test
    void solve() {
        Day12 solver = new Day12(List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"));
        long sumOfArrangements = solver.solve();

        assertThat(sumOfArrangements, is(21L));
    }

    @Test
    void solveActualInput() {
        List<String> list = readLines(Day12Test.class.getResourceAsStream("/day12/day12_input.txt"), UTF_8);
        Day12 solver = new Day12(list);
        long sumOfArrangements = solver.solve();

        assertThat(sumOfArrangements, is(6852L));
    }


    @Test
    void arrangementsPart2() {
        long arrangements = solver.calc("???.###????.###????.###????.###????.###", newArrayList(1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3));

        assertThat(arrangements, is(1L));
    }

    @Test
    void arrangements2Part2() {
        long arrangements = solver.calc(".??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##.",
                newArrayList(1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3));

        assertThat(arrangements, is(16384L));
    }

    @Test
    void arrangements3Part2() {
        long arrangements = solver.calc("?#?#?#?#?#?#?#???#?#?#?#?#?#?#???#?#?#?#?#?#?#???#?#?#?#?#?#?#???#?#?#?#?#?#?#?",
                newArrayList(1, 3, 1, 6, 1, 3, 1, 6, 1, 3, 1, 6, 1, 3, 1, 6, 1, 3, 1, 6));

        assertThat(arrangements, is(1L));
    }

    @Test
    void arrangements4Part2() {
        long arrangements = solver.calc("????.#...#...?????.#...#...?????.#...#...?????.#...#...?????.#...#...",
                newArrayList(4, 1, 1, 4, 1, 1, 4, 1, 1, 4, 1, 1, 4, 1, 1));

        assertThat(arrangements, is(16L));
    }

    @Test
    void arrangements5Part2() {
        long arrangements = solver.calc("????.######..#####.?????.######..#####.?????.######..#####.?????.######..#####.?????.######..#####.",
                newArrayList(1, 6, 5, 1, 6, 5, 1, 6, 5, 1, 6, 5, 1, 6, 5));

        assertThat(arrangements, is(2500L));
    }

    @Test
    void arrangements6Part2() {
        long arrangements = solver.calc("?###??????????###??????????###??????????###??????????###????????", newArrayList(3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1, 3, 2, 1));

        assertThat(arrangements, is(506250L));
    }

    @Test
    void solvePart2() {
        Day12 solver = new Day12(List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"));
        long sumOfArrangements = solver.solvePart2(5);

        assertThat(sumOfArrangements, is(525152L));
    }


    @Test
    void solvePart2ActualInput() {
        List<String> list = readLines(Day12Test.class.getResourceAsStream("/day12/day12_input.txt"), UTF_8);
        Day12 solver = new Day12(list);
        long sumOfArrangements = solver.solvePart2(5);

        assertThat(sumOfArrangements, is(greaterThan(1978351685L)));
        assertThat(sumOfArrangements, is(8475948826693L));
    }
}