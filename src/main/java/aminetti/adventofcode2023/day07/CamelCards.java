package aminetti.adventofcode2023.day07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static aminetti.adventofcode2023.day07.CamelCards.Hand.Kind.*;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.*;

public class CamelCards {
    private static final Logger LOGGER = LoggerFactory.getLogger(CamelCards.class);

    public Hand readCardAndBet(String s) {
        Pattern regex = Pattern.compile("(.{5}) (\\d+)"); // trusting the input

        Matcher matcher = regex.matcher(s);

        if (matcher.find()) {
            return new Hand(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }

        throw new IllegalArgumentException("Wrong format for hand!");
    }

    public long solve(List<String> lines) {
        List<Hand> reversedRankedHands = lines.stream()
                .map(this::readCardAndBet)
                .sorted()
                .toList();

        int totalWinning = 0;

        int rank = 1;
        for (Hand rankedHand : reversedRankedHands) {
            int score = rank * rankedHand.bet;
            LOGGER.info("Hand {}, revRank {}, score {}", rankedHand, rank, score);
            totalWinning += score;
            rank++;
        }

        return totalWinning;
    }

    public record Hand(String cards, int bet) implements Comparable<Hand> {

        public enum Kind {
            HIGH_CARD, ONE_PAIR, TWO_PAIRS, THREE_OF_K, FULL_HOUSE, FOUR_OF_K, FIVE_OF_K
        }

        public Kind kind() {
            List<Long> bestGroups = cards.chars()
                    .boxed()
                    .collect(groupingBy(Function.identity(), counting()))
                    .values().stream().sorted(reverseOrder())
                    .toList();

            Long bestGroup = bestGroups.get(0);
            if (bestGroup == 5) return FIVE_OF_K;
            if (bestGroup == 4) return FOUR_OF_K;

            Long secondBestGroup = bestGroups.get(1);
            if (bestGroup == 3) {
                return secondBestGroup == 2 ? FULL_HOUSE : THREE_OF_K;
            }
            if (bestGroup == 2) {
                return secondBestGroup == 2 ? TWO_PAIRS : ONE_PAIR;
            }
            return HIGH_CARD;
        }

        @Override
        public int compareTo(Hand o) {
            int compareByKind = this.kind().compareTo(o.kind());
            if (compareByKind != 0) return compareByKind; // different kind


            int compare = 0;
            for (int i = 0; i < 5; i++) {
                compare = Integer.compare(
                        toLabelValue(this.cards.charAt(i)),
                        toLabelValue(o.cards.charAt(i))
                );
                if (compare != 0) break;
            }
            return compare;
        }

        public static int toLabelValue(char c) {
            return switch (c) {
                case 'T' -> 10;
                case 'J' -> 11;
                case 'Q' -> 12;
                case 'K' -> 13;
                case 'A' -> 14;
                default -> Character.getNumericValue(c);
            };
        }
    }
}
