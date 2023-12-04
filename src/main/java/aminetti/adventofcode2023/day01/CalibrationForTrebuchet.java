package aminetti.adventofcode2023.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.reverse;

public class CalibrationForTrebuchet {
    private static final CalibrationForTrebuchet INSTANCE = new CalibrationForTrebuchet();

    private final NumberExtractor numberExtractor = new NumberExtractor();

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected one parameter for the input file path.");
        }

        Path file = Path.of(args[0]);
        if (Files.isDirectory(file) || Files.notExists(file)) {
            throw new IllegalArgumentException("The specified file does not exist.");
        }

        try (Stream<String> lines = Files.lines(file)) {
            System.out.println("The result for the first task is: " + INSTANCE.solveTask1(lines));
        }

        try (Stream<String> lines = Files.lines(file)) {
            System.out.println("The result for the second task is: " + INSTANCE.solveTask2(lines));
        }
    }

    public int solveTask1(Stream<String> stringStream) {
        return stringStream.mapToInt(this::calibrateWithDigitsOnly).sum();
    }

    int calibrateWithDigitsOnly(String s) {
        int firstNumber = numberExtractor.extractFirstDigit(s);
        int lastNumber = numberExtractor.extractFirstDigit(reverse(s));
        return firstNumber * 10 + lastNumber;
    }

    public int solveTask2(Stream<String> stringStream) {
        return stringStream.mapToInt(this::calibrateWithDigitsAndWords).sum();
    }

    int calibrateWithDigitsAndWords(String s) {
        int firstNumber = numberExtractor.extractFirstDigitOrSpelledNumber(s);
        int lastNumber = numberExtractor.extractLastDigitOrSpelledNumber(s);
        return firstNumber * 10 + lastNumber;
    }

}