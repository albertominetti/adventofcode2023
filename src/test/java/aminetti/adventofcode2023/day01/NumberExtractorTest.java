package aminetti.adventofcode2023.day01;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class NumberExtractorTest {

    private final NumberExtractor numberExtractor = new NumberExtractor();

    @Test
    void whenOnlyDigitsAreConsidered() {
        int result = numberExtractor.extractFirstDigit("astwodf35632ghjk2fgh");

        assertThat(result, is(3));
    }

    @Test
    void whenTheWordTwoIsTheFirstWord() {
        int result = numberExtractor.extractFirstDigitOrSpelledNumber("astwodf35632ghjk2fgh");

        assertThat(result, is(2));
    }

    @Test
    void whenAnNumberInDigitIsTheFirsNumber() {
        int result = numberExtractor.extractFirstDigitOrSpelledNumber("ast2wodf35632ghjk2fgh");

        assertThat(result, is(2));
    }


    @Test
    void whenTheWordTwoIsTheLastWord() {
        int result = numberExtractor.extractLastDigitOrSpelledNumber("astwodf35632ghjk9ftwogh");

        assertThat(result, is(2));
    }
}