package aminetti.adventofcode2023.day04;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.join;
import static java.util.Collections.nCopies;

public class PileOfScratchCards {
    private static final Logger LOGGER = LoggerFactory.getLogger(PileOfScratchCards.class);
    private final int winningNumbers;
    private final int playedNumbers;
    private final Pattern pattern;

    public PileOfScratchCards(int winningNumbers, int playedNumbers) {
        this.winningNumbers = winningNumbers;
        this.playedNumbers = playedNumbers;

        String digits = "(\\d+)";
        String winning = join(" +", nCopies(winningNumbers, digits));
        String played = join(" +", nCopies(playedNumbers, digits));

        String regex = "Card +\\d+: +%s \\| +%s".formatted(winning, played);
        LOGGER.info("Regex {}", regex);

        pattern = Pattern.compile(regex);
    }

    public int calculatePoints(List<String> lines) {
        int score = 0;
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                Set<Integer> winningNums = new HashSet<>();
                int i = 1;
                for (; i <= winningNumbers; i++) {
                    LOGGER.debug("Match: {}", matcher.group(i));
                    winningNums.add(parseInt(matcher.group(i)));
                }
                Set<Integer> playedNums = new HashSet<>();
                for (; i <= winningNumbers + playedNumbers; i++) {
                    LOGGER.debug("Match: {}", matcher.group(i));
                    playedNums.add(parseInt(matcher.group(i)));
                }
                Collection<Integer> wins = CollectionUtils.intersection(winningNums, playedNums);

                score += (int) Math.pow(2, wins.size() - 1);

            }
        }

        return score;
    }

    public int calculateWinningCards(List<String> lines) {
        HashMap<Integer, Integer> winnedCards = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            winnedCards.put(i, 1);
        }

        for (int i = 0; i < lines.size(); i++) {
            int nextCards = calculateCards(lines, i);
            int instancesOfThisCard = winnedCards.get(i);
            for (int j = 1; j <= nextCards && i + j < lines.size(); j++) {
                winnedCards.compute(i + j, (k, v) -> v + instancesOfThisCard);
            }
        }

        return winnedCards.values().stream().mapToInt(v -> v).sum();
    }

    public int calculateCards(List<String> lines, int lineNum) {
        int nextCards = 0;
        String line = lines.get(lineNum);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            Set<Integer> winningNums = new HashSet<>();
            int i = 1;
            for (; i <= winningNumbers; i++) {
                LOGGER.debug("Match: {}", matcher.group(i));
                winningNums.add(parseInt(matcher.group(i)));
            }
            Set<Integer> playedNums = new HashSet<>();
            for (; i <= winningNumbers + playedNumbers; i++) {
                LOGGER.debug("Match: {}", matcher.group(i));
                playedNums.add(parseInt(matcher.group(i)));
            }
            Collection<Integer> wins = CollectionUtils.intersection(winningNums, playedNums);

            nextCards += wins.size();

        }

        return nextCards;
    }


}
