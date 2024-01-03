package aminetti.adventofcode2023.day12;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static aminetti.adventofcode2023.day12.Day12.calc;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day12Test {

    @Test
    void arrangements() {
        int arrangements = calc("???.###", Lists.newArrayList(1, 1, 3));

        assertThat(arrangements, is(1));
    }

    @Test
    void arrangements2() {
        int arrangements = calc(".??..??...?##.", Lists.newArrayList(1, 1, 3));

        assertThat(arrangements, is(4));
    }

    @Test
    void arrangements3() {
        int arrangements = calc("?#?#?#?#?#?#?#?", Lists.newArrayList(1, 3, 1, 6));

        assertThat(arrangements, is(1));
    }

    @Test
    void arrangements4() {
        int arrangements = calc("????.#...#...", Lists.newArrayList(4, 1, 1));

        assertThat(arrangements, is(1));
    }

    @Test
    void arrangements5() {
        int arrangements = calc("????.######..#####.", Lists.newArrayList(1, 6, 5));

        assertThat(arrangements, is(4));
    }

    @Test
    void arrangements6() {
        int arrangements = calc("?###????????", Lists.newArrayList(3, 2, 1));

        assertThat(arrangements, is(10));
    }

    @Test
    void solve() {
        Day12 solver = new Day12(List.of("???.### 1,1,3",
                ".??..??...?##. 1,1,3",
                "?#?#?#?#?#?#?#? 1,3,1,6",
                "????.#...#... 4,1,1",
                "????.######..#####. 1,6,5",
                "?###???????? 3,2,1"));
        int sumOfArrangements = solver.solve();

        assertThat(sumOfArrangements, is(21));
    }

    @Test
    void solveActualInput() {
        List<String> list = readLines(Day12Test.class.getResourceAsStream("/day12/day12_input.txt"), UTF_8);
        Day12 solver = new Day12(list);
        int sumOfArrangements = solver.solve();

        assertThat(sumOfArrangements, is(6852));
    }
}