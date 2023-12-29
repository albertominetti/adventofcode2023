package aminetti.adventofcode2023.day10;

import aminetti.adventofcode2023.day10.PipeMaze.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PipeMazeTest {

    List<String> testInput = List.of(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ"
    );

    @Test
    void readInput() {
        char[][] maze = PipeMaze.readMaze(testInput);

        assertThat(maze[0][0], is('7'));
        assertThat(maze[3][3], is('-'));
        assertThat(maze[2][1], is('J'));
        assertThat(maze[4][1], is('J'));
    }


    @Test
    void findAnimal() {
        PipeMaze solver = new PipeMaze(testInput);
        Coordinate coords = solver.findAnimal();

        assertThat(coords.row(), is(2));
        assertThat(coords.col(), is(0));
    }

    @Test
    void findStartAndEnd() {

        PipeMaze solver = new PipeMaze(testInput);
        Coordinate animal = solver.findAnimal();
        List<Coordinate> startAndEnd = solver.findStartAndEnd(animal);

        System.out.println(startAndEnd);

        assertThat(startAndEnd.get(0).row(), is(3));
        assertThat(startAndEnd.get(0).col(), is(0));


        assertThat(startAndEnd.get(1).row(), is(2));
        assertThat(startAndEnd.get(1).col(), is(1));
    }

    @Test
    void findAnimalRealInput() {
        List<String> list = readLines(PipeMazeTest.class.getResourceAsStream("/day10/day10_input.txt"), UTF_8);

        PipeMaze solver = new PipeMaze(list);
        Coordinate coords = solver.findAnimal();
        System.out.println(coords);

        assertThat(coords.row(), is(112));
        assertThat(coords.col(), is(18));
    }


    @Test
    void solve() {
        PipeMaze solver = new PipeMaze(testInput);
        long solve = solver.solvePart1();

        assertThat(solve, is(8L));
    }


    @Test
    void solvePart1() {
        List<String> list = readLines(PipeMazeTest.class.getResourceAsStream("/day10/day10_input.txt"), UTF_8);

        PipeMaze solver = new PipeMaze(list);
        long solve = solver.solvePart1();

        assertThat(solve, is(not(26L)));
        assertThat(solve, is(7097L));

    }

    @Test
    void solvePart2TestData() {
        List<String> list = List.of(
                "FF7FSF7F7F7F7F7F---7",
                "L|LJ||||||||||||F--J",
                "FL-7LJLJ||||||LJL-77",
                "F--JF--7||LJLJIF7FJ-",
                "L---JF-JLJIIIIFJLJJ7",
                "|F|F-JF---7IIIL7L|7|",
                "|FFJF7L7F-JF7IIL---7",
                "7-L-JL7||F7|L7F-7F7|",
                "L.L7LFJ|||||FJL7||LJ",
                "L7JLJL-JLJLJL--JLJ.L");

        PipeMaze solver = new PipeMaze(list);
        long solve = solver.solvePart2();

        assertThat(solve, is(10L));

    }

    @Test
    void solvePart2() {
        List<String> list = readLines(PipeMazeTest.class.getResourceAsStream("/day10/day10_input.txt"), UTF_8);

        PipeMaze solver = new PipeMaze(list);
        long solve = solver.solvePart2();

        assertThat(solve, lessThan(4800L));
        assertThat(solve, lessThan(600L));
        assertThat(solve, is(355L));

    }

}