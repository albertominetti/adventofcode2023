package aminetti.adventofcode2023.day07;

import aminetti.adventofcode2023.day07.CamelCards.Hand;
import aminetti.adventofcode2023.day07.CamelCards.HandWithJoker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static aminetti.adventofcode2023.day07.CamelCards.Kind.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CamelCardsTest {

    CamelCards solver = new CamelCards();

    @Test
    void solveTestInputPart1() {
        long totalWining = solver.solve(
                List.of(
                        "32T3K 765",
                        "T55J5 684",
                        "KK677 28",
                        "KTJJT 220",
                        "QQQJA 483"
                ));

        assertThat(totalWining, is((long) 6440));
    }

    @Test
    void parserTest() {
        Hand hand = solver.readCardAndBet("32T3K 765");

        assertThat(hand.cards(), is("32T3K"));
        assertThat(hand.bet(), is(765));
    }

    @Test
    void testKindCalculation() {
        assertThat(new Hand("32T3K", 765).kind(), is(ONE_PAIR));
        assertThat(new Hand("KK677", 684).kind(), is(TWO_PAIRS));
        assertThat(new Hand("KTJJT", 28).kind(), is(TWO_PAIRS));
        assertThat(new Hand("QQQJA", 220).kind(), is(THREE_OF_K));
        assertThat(new Hand("AKAKA", 483).kind(), is(FULL_HOUSE));
        assertThat(new Hand("32165", 1).kind(), is(HIGH_CARD));
        assertThat(new Hand("35555", 1).kind(), is(FOUR_OF_K));
        assertThat(new Hand("11111", 2).kind(), is(FIVE_OF_K));
    }

    @Test
    void testComparatorBasedOnKindOnly() {
        assertThat(new Hand("11111", 2), greaterThan(new Hand("32165", 1)));
        assertThat(new Hand("KK677", 684), greaterThan(new Hand("32T3K", 765)));
        assertThat(new Hand("QQQJA", 220), greaterThan(new Hand("32T3K", 765)));
        assertThat(new Hand("QQQJA", 220), lessThan(new Hand("35555", 1)));
    }

    @Test
    void testComparatorBasedOnKindAndOrder() {
        assertThat(new Hand("11111", 2), lessThan(new Hand("22222", 1)));
        assertThat(new Hand("KK677", 684), greaterThan(new Hand("776KK", 765)));
        assertThat(new Hand("QQQJA", 220), lessThan(new Hand("AAA96", 765)));
        assertThat(new Hand("91111", 220), greaterThan(new Hand("35555", 1)));
    }

    @Test
    void solvePart1() {
        List<String> list = readLines(CamelCardsTest.class.getResourceAsStream("/day07/day7_input.txt"), UTF_8);

        long marginError = solver.solve(list);

        assertThat(marginError, is((long) 251106089));
    }

    // part 2 with joker


    @Test
    void solveTestInputPart2() {
        long totalWining = solver.solvePart2(
                List.of(
                        "32T3K 765",
                        "T55J5 684",
                        "KK677 28",
                        "KTJJT 220",
                        "QQQJA 483"
                ));

        assertThat(totalWining, is((long) 5905));
    }


    @Test
    void testKindCalculationPart2() {
        assertThat(new HandWithJoker("32T3K", 765).kind(), is(ONE_PAIR));
        assertThat(new HandWithJoker("KK677", 684).kind(), is(TWO_PAIRS));
        assertThat(new HandWithJoker("KTJJT", 28).kind(), is(FOUR_OF_K));
        assertThat(new HandWithJoker("QQQJA", 220).kind(), is(FOUR_OF_K));
        assertThat(new HandWithJoker("AKAKA", 483).kind(), is(FULL_HOUSE));
        assertThat(new HandWithJoker("32165", 1).kind(), is(HIGH_CARD));
        assertThat(new HandWithJoker("35555", 1).kind(), is(FOUR_OF_K));
        assertThat(new HandWithJoker("11111", 2).kind(), is(FIVE_OF_K));
        assertThat(new HandWithJoker("JJJJ1", 2).kind(), is(FIVE_OF_K));
    }

    @Test
    void testComparatorBasedOnKindOnlyPart2() {
        assertThat(new HandWithJoker("11111", 2), greaterThan(new HandWithJoker("32165", 1)));
        assertThat(new HandWithJoker("KK677", 684), greaterThan(new HandWithJoker("32T3K", 765)));
        assertThat(new HandWithJoker("QQQJA", 220), greaterThan(new HandWithJoker("32T3K", 765)));
        assertThat(new HandWithJoker("QQQJA", 220), greaterThan(new HandWithJoker("35555", 1)));
    }

    @Test
    void testComparatorBasedOnKindAndOrderPart2() {
        assertThat(new HandWithJoker("11111", 2), lessThan(new HandWithJoker("22222", 1)));
        assertThat(new HandWithJoker("KK677", 684), greaterThan(new HandWithJoker("776KK", 765)));
        assertThat(new HandWithJoker("QQQJA", 220), greaterThan(new HandWithJoker("AAA96", 765)));
        assertThat(new HandWithJoker("91111", 220), greaterThan(new HandWithJoker("35555", 1)));
        assertThat(new HandWithJoker("J1111", 220), lessThan(new HandWithJoker("AAAAA", 1)));
        assertThat(new HandWithJoker("3J333", 220), greaterThan(new HandWithJoker("2J222", 1)));
        assertThat(new HandWithJoker("J3333", 220), lessThan(new HandWithJoker("2J222", 1)));
    }

    @Test
    void solvePart2() {
        List<String> list = readLines(CamelCardsTest.class.getResourceAsStream("/day07/day7_input.txt"), UTF_8);

        long marginError = solver.solvePart2(list);

        assertThat(marginError, greaterThan((long) 248209474));
        assertThat(marginError, greaterThan((long) 248439515));
        assertThat(marginError, is((long) 249620106));
    }

}