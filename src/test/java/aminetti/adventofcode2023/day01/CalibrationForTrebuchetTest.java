package aminetti.adventofcode2023.day01;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class CalibrationForTrebuchetTest {
    private final CalibrationForTrebuchet calibrationForTrebuchet = new CalibrationForTrebuchet();

    /* Use case:
     1. when both numbers are in the extremes of the line, and no other numbers in the middle
     2. when both numbers are in the extremes of the line, and there are other numbers in the middle
     3. when both numbers are in the intern of the line, and no other numbers in the middle
     4. when both numbers are in the intern of the line, and there are other numbers in the middle
     5. when there is a single number in the string surrounded by other characters
     6. when there is a single number in the string, and no other numbers

     7. when an number is extern and one is intern of the line, and no other numbers in the middle
     8. when an number is extern and one is intern of the line, and there are other numbers in the middle
    */

    @Test
    void whenBothNumbersAreInTheExtremesOfTheLineAndNoOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("1asdasdasd2");

        assertThat(result, is(12));
    }

    @Test
    void whenBothNumbersAreInTheExtremesOfTheLineAndThereAreOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("1asdasd2kkk9asd2");

        assertThat(result, is(12));
    }

    @Test
    void whenBothNumbersAreInTheInternOfTheLineAndNoOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdfghjk1asdassd2asdfgh");

        assertThat(result, is(12));
    }


    @Test
    void whenBothNumbersAreInTheInternOfTheLineAndThereAreOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdfghjk1asd6666asd2kkk9asd2asdfgh");

        assertThat(result, is(12));
    }

    @Test
    void whenThereIsASingleNumberInTheStringSurroundedByOtherCharacters() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdf6fgh");

        assertThat(result, is(66));
    }

    @Test
    void whenThereIsASingleNumberInTheStringAndNoOtherNumbers() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("6");

        assertThat(result, is(66));
    }

    @Test
    void whenAnNumberIsExternAndOneIsInternOfTheLineAndNoOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("9asdfghjk2fgh");

        assertThat(result, is(92));
    }

    @Test
    void whenAnNumberIsExternAndOneIsInternOfTheLineAndThereAreOtherNumbersInTheMiddle() {
        int result = calibrationForTrebuchet.calibrateWithDigitsAndWords("9asdf85632ghjk2fgh");

        assertThat(result, is(92));
    }

    @Test
    void assertTheSameBehaviorForMostOfTheInputs() {
        int resultTask1, resultTask2;
        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("9asdf85632ghjk2fgh");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("9asdf85632ghjk2fgh");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("9asdfghjk2fgh");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("9asdfghjk2fgh");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("6");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("6");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("asdf6fgh");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdf6fgh");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("asdfghjk1asd6666asd2kkk9asd2asdfgh");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdfghjk1asd6666asd2kkk9asd2asdfgh");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("asdfghjk1asdassd2asdfgh");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("asdfghjk1asdassd2asdfgh");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("1asdasd2kkk9asd2");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("1asdasd2kkk9asd2");
        assertThat(resultTask1, is(resultTask2));

        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("1asdasdasd2");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("1asdasdasd2");
        assertThat(resultTask1, is(resultTask2));

    }


    @Test
    void assertTheDifferentBehaviorForSpecificInputs() {
        int resultTask1, resultTask2;
        resultTask1 = calibrationForTrebuchet.calibrateWithDigitsOnly("9asdf85632ghjk2fghseven");
        resultTask2 = calibrationForTrebuchet.calibrateWithDigitsAndWords("9asdf85632ghjk2fghseven");
        assertThat(resultTask1, is(not(resultTask2)));
    }


    @Test
    void checkThatAllTheSummingWorksProperlyWithStream() {
        int sumResult = calibrationForTrebuchet.solveTask2(Stream.of("1asdasda5", "asdasd9asdasd", "asd30asdasd"));

        assertThat(sumResult, is(15 + 99 + 30));
    }

    @Test
    void solveUsingAnInputFile() {
        List<String> list = readLines(CalibrationForTrebuchetTest.class.getResourceAsStream("/day01/day1_test_input.txt"), UTF_8);

        int totalSum = calibrationForTrebuchet.solveTask2(list.stream());

        assertThat(totalSum, is(49 * 3 + 44 + 44 + 56 + 59));
    }

    @Test
    void solveUsingTheActualInputFileForTask1() {
        List<String> list = readLines(CalibrationForTrebuchetTest.class.getResourceAsStream("/day01/day1_input.txt"), UTF_8);

        int totalSum = calibrationForTrebuchet.solveTask1(list.stream());

        assertThat(totalSum, is(53921));
    }

    @Test
    void solveUsingTheActualInputFileForTask2() {
        List<String> list = readLines(CalibrationForTrebuchetTest.class.getResourceAsStream("/day01/day1_input.txt"), UTF_8);

        int totalSum = calibrationForTrebuchet.solveTask2(list.stream());

        assertThat(totalSum, is(54676));
    }

}