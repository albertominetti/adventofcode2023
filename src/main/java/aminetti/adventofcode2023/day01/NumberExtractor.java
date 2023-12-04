package aminetti.adventofcode2023.day01;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.lang.String.join;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.reverse;

public class NumberExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberExtractor.class);

    private static final List<String> SPELLED_NUMS = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
    private static final Map<String, Integer> MAP_WORD_TO_VALUE = IntStream.range(0, SPELLED_NUMS.size())
            .boxed().collect(toMap(SPELLED_NUMS::get, i -> i + 1));

    private static final List<String> REVERSED_SPELLED_NUMS = SPELLED_NUMS.stream().map(StringUtils::reverse).collect(toList());
    private static final Map<String, Integer> MAP_REVERSED_WORD_TO_VALUE = IntStream.range(0, REVERSED_SPELLED_NUMS.size())
            .boxed().collect(toMap(REVERSED_SPELLED_NUMS::get, i -> i + 1));

    private final Pattern patternForFirstDigit, patternForFirstNum, patternForLastNum;

    public NumberExtractor() {
        patternForFirstDigit = Pattern.compile("(\\d)");

        String regexForFirstNum = "(\\d|" + join("|", SPELLED_NUMS) + ")";
        LOGGER.debug("The pattern for regexForFirstNum is {}", regexForFirstNum);
        patternForFirstNum = Pattern.compile(regexForFirstNum);

        String regexForLastNum = "(\\d|" + join("|", REVERSED_SPELLED_NUMS) + ")";
        LOGGER.debug("The pattern for regexForLastNum is {}", regexForLastNum);
        patternForLastNum = Pattern.compile(regexForLastNum);
    }

    public int extractFirstDigit(String s) {
        Matcher matcher = patternForFirstDigit.matcher(s);
        if (!matcher.find()) {
            throw new IllegalArgumentException("A number (digit) was expected but not found at all!");
        }
        String number = matcher.group();
        return parseInt(number);
    }

    public int extractFirstDigitOrSpelledNumber(String s) {
        Matcher matcher = patternForFirstNum.matcher(s);
        if (!matcher.find()) {
            throw new IllegalArgumentException("A number (digit or spelled number) was expected but not found at all!");
        }
        String number = matcher.group();
        return isDigit(number.charAt(0)) ? parseInt(number)
                : MAP_WORD_TO_VALUE.get(number);
    }

    public int extractLastDigitOrSpelledNumber(String s) {
        Matcher matcher = patternForLastNum.matcher(reverse(s));
        if (!matcher.find()) {
            throw new IllegalArgumentException("A number (digit or spelled number) was expected but not found at all!");
        }
        String number = matcher.group();
        return isDigit(number.charAt(0)) ? parseInt(number)
                : MAP_REVERSED_WORD_TO_VALUE.get(number);
    }

}
