package aminetti.adventofcode2023.day10;

import aminetti.adventofcode2023.day10.PipeMaze.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class PipeMazeTest {

    PipeMaze solver = new PipeMaze();

    List<String> testInput = List.of(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ"
    );


    @Test
    void readInput() {
        char[][] maze = solver.readMaze(testInput);

        assertThat(maze[0][0], is('7'));
        assertThat(maze[3][3], is('-'));
        assertThat(maze[2][1], is('J'));
        assertThat(maze[4][1], is('J'));
    }


    @Test
    void findAnimal() {
        char[][] maze = solver.readMaze(testInput);

        Coordinate coords = solver.findAnimal(maze);

        assertThat(coords.row(), is(2));
        assertThat(coords.col(), is(0));
    }

    @Test
    void findPipeNearAnimal() {
        char[][] maze = solver.readMaze(testInput);

        Coordinate animal = solver.findAnimal(maze);
        Coordinate nearPipe = solver.findOnePipeNearAnimal(maze, animal);

        System.out.println(nearPipe);

        assertThat(nearPipe.row(), is(3));
        assertThat(nearPipe.col(), is(0));
    }


    @Test
    void findStartAndEnd() {
        char[][] maze = solver.readMaze(testInput);

        Coordinate animal = solver.findAnimal(maze);
        List<Coordinate> startAndEnd = solver.findStartAndEnd(maze, animal);

        System.out.println(startAndEnd);

        assertThat(startAndEnd.get(0).row(), is(3));
        assertThat(startAndEnd.get(0).col(), is(0));


        assertThat(startAndEnd.get(1).row(), is(2));
        assertThat(startAndEnd.get(1).col(), is(1));
    }


    @Test
    void findPipeNearAnimalBis() {

        List<String> testInput = List.of(
                "7-F7-",
                ".FJ|7",
                "SJLL7",
                "FF--J",
                "LJ.LJ"
        );
        char[][] maze = solver.readMaze(testInput);

        Coordinate animal = solver.findAnimal(maze);
        Coordinate nearPipe = solver.findOnePipeNearAnimal(maze, animal);

        System.out.println("Coordinate: " + nearPipe + " shape: " + maze[nearPipe.row()][nearPipe.col()]);

        assertThat(nearPipe.row(), is(2));
        assertThat(nearPipe.col(), is(1));
    }

    @Test
    void findPipeNearAnimalTris() {

        List<String> testInput = List.of(
                "7-F7-",
                ".FJ|7",
                "S-LL7",
                "FF--J",
                "LJ.LJ"
        );
        char[][] maze = solver.readMaze(testInput);

        Coordinate animal = solver.findAnimal(maze);
        Coordinate nearPipe = solver.findOnePipeNearAnimal(maze, animal);

        System.out.println("Coordinate: " + nearPipe + " shape: " + maze[nearPipe.row()][nearPipe.col()]);

        assertThat(nearPipe.row(), is(2));
        assertThat(nearPipe.col(), is(1));
    }


    @Test
    void findAnimalRealInput() {
        List<String> list = readLines(PipeMazeTest.class.getResourceAsStream("/day10/day10_input.txt"), UTF_8);

        char[][] maze = solver.readMaze(list);

        Coordinate coords = solver.findAnimal(maze);
        System.out.println(coords);

        assertThat(coords.row(), is(112));
        assertThat(coords.col(), is(18));
    }


    @Test
    void solve() {
        long solve = solver.solvePart1(testInput);

        assertThat(solve, is(8L));
    }


    @Test
    void solvePart1() {
        List<String> list = readLines(PipeMazeTest.class.getResourceAsStream("/day10/day10_input.txt"), UTF_8);

        long solve = solver.solvePart1(list);

        assertThat(solve, is(not(26L)));
        assertThat(solve, is(7097L));

    }

}