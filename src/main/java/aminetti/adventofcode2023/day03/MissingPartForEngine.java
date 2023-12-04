package aminetti.adventofcode2023.day03;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MissingPartForEngine {

    public char[][] toBoard(List<String> lines) {
        char[][] board = new char[lines.size()][lines.get(0).length()];
        int row = 0;
        for (String line : lines) {
            int col = 0;
            for (char c : line.toCharArray()) {
                board[row][col] = c;
                col++;
            }
            row++;
        }
        return board;
    }

    public List<Coordinate> allSymbol(char[][] board) {
        List<Coordinate> coordinates = new ArrayList<>();
        final int ROWS = board.length;
        final int COLS = board[0].length;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (isSymbol(board[i][j])) {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }
        return coordinates;
    }


    public Set<Coordinate> allNumbersNearToCoordinates(char[][] board,
                                                       List<Coordinate> coordinates) {
        Set<Coordinate> numbers = new HashSet<>();

        for (Coordinate coordinate : coordinates) {
            numbers.addAll(numbersNearToCoordinate(board, coordinate));
        }

        return numbers;
    }

    private Set<Coordinate> numbersNearToCoordinate(char[][] board, Coordinate coordinate) {
        Set<Coordinate> numbersCoordinates = new HashSet<>();

        final int ROWS = board.length;
        final int COLS = board[0].length;

        final int row = coordinate.row();
        final int col = coordinate.col();

        List<Coordinate> directions = List.of(new Coordinate(-1, -1), new Coordinate(-1, 0), new Coordinate(-1, 1),
                new Coordinate(0, -1), new Coordinate(0, 1),
                new Coordinate(1, -1), new Coordinate(1, 0), new Coordinate(1, 1));

        for (Coordinate direction : directions) {

            int i = row - direction.row();
            int j = col - direction.col();

            if (i < 0 || i >= ROWS || j < 0 || j >= COLS) break;

            if (Character.isDigit(board[i][j])) {

                while (Character.isDigit(board[i][j])) {
                    j--;
                    if (j < 0) break;
                }
                numbersCoordinates.add(new Coordinate(i, j + 1));
            }
        }


        return numbersCoordinates;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    public int sumAllNumbersParts(List<String> lines) {
        char[][] board = toBoard(lines);

        List<Coordinate> symbols = allSymbol(board);

        Set<Coordinate> coordinates = allNumbersNearToCoordinates(board, symbols);

        List<Integer> numbers = allNumbersStartingFromCoordinates(board, coordinates);

        return numbers.stream().mapToInt(v -> v).sum();
    }

    private List<Integer> allNumbersStartingFromCoordinates(char[][] board, Set<Coordinate> coordinates) {
        final int COLS = board[0].length;

        List<Integer> numbers = new ArrayList<>(coordinates.size());

        for (Coordinate coordinate : coordinates) {

            int i = coordinate.row();
            int j = coordinate.col();

            int number = 0;
            while (Character.isDigit(board[i][j])) {
                number = number * 10 + Character.getNumericValue(board[i][j]);
                j++;
                if (j >= COLS) break;
            }

            numbers.add(number);

        }

        return numbers;
    }

    public record Coordinate(int row, int col) {

    }
}
