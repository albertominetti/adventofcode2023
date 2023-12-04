package aminetti.adventofcode2023.day04;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PileOfScratchCardsTest {


    @Test
    void getPoints() {
        PileOfScratchCards solver = new PileOfScratchCards(5, 8);

        int points = solver.calculatePoints(List.of(
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")
        );

        assertThat(points, is(13));
    }


    @Test
    void getPointsForRealInputPart1() {
        PileOfScratchCards solver = new PileOfScratchCards(10, 25);

        List<String> list = readLines(PileOfScratchCardsTest.class.getResourceAsStream("/day04/day4_input.txt"), UTF_8);

        int points = solver.calculatePoints(list);

        assertThat(points, is(21568));
    }
}