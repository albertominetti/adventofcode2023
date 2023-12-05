package aminetti.adventofcode2023.day03;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static aminetti.adventofcode2023.day03.MissingPartForEngine.Coordinate;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.readLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MissingPartForEngineTest {

    MissingPartForEngine service = new MissingPartForEngine();

    @Test
    void inputFromExample() {
        int total = service.sumAllNumbersParts(List.of(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        ));

        assertThat(total, is(4361));
    }

    @Test
    void getAllSymbolsCoordinates() {
        List<Coordinate> coordinates = service.allSymbol(service.toBoard(List.of(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        )));

        assertThat(coordinates, hasSize(6));
        assertThat(coordinates, hasItem(new Coordinate(1, 3)));
        assertThat(coordinates, hasItem(new Coordinate(3, 6)));
        assertThat(coordinates, hasItem(new Coordinate(4, 3)));
        assertThat(coordinates, hasItem(new Coordinate(5, 5)));
        assertThat(coordinates, hasItem(new Coordinate(8, 3)));
        assertThat(coordinates, hasItem(new Coordinate(8, 5)));
    }


    @Test
    void getAllNumbersNearToCoordinate() {
        Set<Coordinate> numbers = service.allNumbersNearToCoordinates(

                service.toBoard(List.of(
                        "467..114..",
                        "...*......",
                        "..35..633."
                )),
                List.of(new Coordinate(1, 3))
        );

        assertThat(numbers, hasSize(2));
        assertThat(numbers, containsInAnyOrder(new Coordinate(0, 0), new Coordinate(2, 2)));
    }

    @Test
    void getAllNumbersNearToCoordinates() {
        Set<Coordinate> numbers = service.allNumbersNearToCoordinates(

                service.toBoard(List.of(
                        "467..114..",
                        ".*.*......",
                        "..35..633."
                )),
                List.of(new Coordinate(1, 3), new Coordinate(1, 1))
        );

        assertThat(numbers, hasSize(2));
        assertThat(numbers, containsInAnyOrder(new Coordinate(0, 0), new Coordinate(2, 2)));
    }

    @Test
    void realInputForPart1() {
        List<String> list = readLines(MissingPartForEngineTest.class.getResourceAsStream("/day03/day3_input.txt"), UTF_8);

        int total = service.sumAllNumbersParts(list);

        assertThat(total, is(520019));
    }


    @Test
    void getAllGearRatioNearStars() {
        int total = service.sumAllGearRatio(
                List.of(
                        "467..114..",
                        "...*......",
                        "..35..633.",
                        "......#...",
                        "617*......",
                        ".....+.58.",
                        "..592.....",
                        "......755.",
                        "...$.*....",
                        ".664.598.."
                )
        );

        assertThat(total, is(467835));
    }

    @Test
    void realInputForPart2() {
        List<String> list = readLines(MissingPartForEngineTest.class.getResourceAsStream("/day03/day3_input.txt"), UTF_8);

        int total = service.sumAllGearRatio(list);

        assertThat(total, is(75519888));
    }


}