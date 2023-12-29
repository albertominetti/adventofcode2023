package aminetti.adventofcode2023.day11;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class Day11 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);

    private final List<Galaxy> galaxies;
    private final List<String> image;
    private final int rows;
    private final int cols;
    private final int expandBy;

    public Day11(List<String> image, int expandBy) {
        this.image = image;
        rows = image.size();
        cols = image.get(0).length();
        this.expandBy = expandBy;
        galaxies = readGalaxies();


    }

    private List<Galaxy> readGalaxies() {
        List<Galaxy> galaxies = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            for (int j = 0; j < image.get(i).length(); j++) {
                if (image.get(i).charAt(j) == '#') {
                    galaxies.add(new Galaxy(i, j));
                }
            }

        }
        return galaxies;
    }

    public long solve() {

        Collection<Integer> emptyCols = CollectionUtils.subtract(
                IntStream.range(0, cols).boxed().collect(toSet()),
                galaxies.stream().map(g -> g.y).collect(toSet()));

        Collection<Integer> emptyRows = CollectionUtils.subtract(
                IntStream.range(0, rows).boxed().collect(toSet()),
                galaxies.stream().map(g -> g.x).collect(toSet()));
        LOGGER.info("Empty cols: {}; Empty rows: {}", emptyCols, emptyRows);

        long totalDistance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {

                Galaxy g1 = galaxies.get(i);
                Galaxy g2 = galaxies.get(j);

                int distance = Math.abs(g1.x - g2.x) + Math.abs(g1.y - g2.y);

                long extraCols = emptyCols.stream().filter(c -> (g1.y < c && c < g2.y) || g2.y < c && c < g1.y).count();
                long extraRows = emptyRows.stream().filter(c -> (g1.x < c && c < g2.x) || g2.x < c && c < g1.x).count();

                LOGGER.info("Distance from {} and {} is {} with {} extra cols and {} extra rows", g1, g2, distance, extraCols, extraRows);

                totalDistance += distance + (extraCols + extraRows) * (expandBy - 1);
            }
        }

        return totalDistance;
    }


    public record Galaxy(int x, int y) {
        @Override
        public String toString() {
            return "(%d,%d)".formatted(x, y);
        }
    }
}
