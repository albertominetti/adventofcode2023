package aminetti.adventofcode2023.day14;

import aminetti.adventofcode2023.day11.Day11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Day14 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);

    private char[][] field;


    public Day14(List<String> input) {
        field = readRocks(input);
    }

    public long solve() {

        print();
        tiltNorth();
        print();

        return calcSum();


    }

    public long solvePart2() {
        print();
        char[][] previous = snapshot();
        for (int cycle = 0; cycle < 1000000000; cycle++) {
            for (int i = 0; i < 4; i++) {
                tiltNorth();
                rotateCw();
            }
            if (same(field, previous)) {
                LOGGER.info("No more changes after {} cycles", cycle - 1);
                break;
            }
            previous = snapshot();
        }
        print();

        return calcSum();
    }

    private void rotateCw() {
        final int rows = field.length;
        final int cols = field[0].length;
        char[][] rotatedField = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedField[j][rows - 1 - i] = field[i][j];
            }
        }
        field = rotatedField;
    }

    private long calcSum() {
        long total = 0;
        for (int i = 0; i < field.length; i++) {
            int weight = field.length - i;
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 'O')
                    total += weight;
            }
        }
        return total;
    }

    private void tiltNorth() {
        final int rows = field.length;
        final int cols = field[0].length;
        for (int i = rows - 1; i >= 1; i--) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == 'O') {

                    int topAvailablePlace = i;
                    int roundRocksToMove = 0;

                    while (true) {

                        if (field[topAvailablePlace][j] == 'O') {
                            roundRocksToMove++;
                        }
                        topAvailablePlace--;
                        if (topAvailablePlace < 0 || field[topAvailablePlace][j] == '#') {
                            break;
                        }

                    }
                    topAvailablePlace++;

                    for (int k = topAvailablePlace; k < topAvailablePlace + roundRocksToMove; k++) {
                        field[k][j] = 'O';
                    }
                    for (int k = topAvailablePlace + roundRocksToMove; k <= i; k++) {
                        field[k][j] = '.';
                    }
                }
            }
        }
    }

    private void print() {
        final int rows = field.length;
        final int cols = field[0].length;
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(field[i][j]);
            }
        }
        System.out.println();
    }


    private char[][] snapshot() {
        final int rows = field.length;
        final int cols = field[0].length;
        char[][] copy = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = field[i][j];
            }
        }
        return copy;
    }

    private boolean same(char[][] a, char[][] b) {
        final int rows = a.length;
        final int cols = a[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    public static char[][] readRocks(List<String> lines) {
        char[][] field = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            field[i] = lines.get(i).toCharArray();
        }

        return field;
    }
}
