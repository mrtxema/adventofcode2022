import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SurfaceAreaAnalyzer {

    public int calculateSurfaceArea(List<Position> cubePositions) {
        int adjacencies = (int) IntStream.range(1, cubePositions.size())
                .mapToLong(i -> IntStream.range(0, i).filter(j -> areAdjacent(cubePositions.get(i), cubePositions.get(j))).count())
                .sum();
        return cubePositions.size() * 6 - adjacencies * 2;
    }

    public int calculateOutsideSurfaceArea(List<Position> cubePositions) {
        Range xRange = Range.from(cubePositions.stream().mapToInt(Position::x).summaryStatistics());
        Range yRange = Range.from(cubePositions.stream().mapToInt(Position::y).summaryStatistics());
        Range zRange = Range.from(cubePositions.stream().mapToInt(Position::z).summaryStatistics());
        Range3d range3d = new Range3d(xRange, yRange, zRange);
        List<Position> insideCubes = xRange.stream().boxed()
                .flatMap(x -> yRange.stream().boxed()
                        .flatMap(y -> zRange.stream().mapToObj(z -> new Position(x, y, z))))
                .filter(position -> !cubePositions.contains(position))
                .filter(position -> isInside(cubePositions, position, range3d))
                .toList();
        System.out.println("Inside cubes: " + insideCubes.size());
        int internalAdjacencies = (int) cubePositions.stream()
                .mapToLong(cubePosition -> insideCubes.stream().filter(insidePosition -> areAdjacent(cubePosition, insidePosition)).count())
                .sum();
        return calculateSurfaceArea(cubePositions) - internalAdjacencies;
    }

    private boolean isInside(List<Position> cubePositions, Position position, Range3d range3d) {
        Set<Position> processed = new HashSet<>();
        Deque<Position> pending = new ArrayDeque<>();
        pending.add(position);
        while (!pending.isEmpty()) {
            Position next = pending.pollFirst();
            if (range3d.isInLimit(next)) {
                return false;
            }
            processed.add(next);
            next.getAllAdjacents()
                    .filter(candidate -> !cubePositions.contains(candidate))
                    .filter(candidate -> !processed.contains(candidate))
                    .filter(candidate -> !pending.contains(candidate))
                    .forEach(pending::add);
        }
        return true;
    }

    private boolean areAdjacent(Position cube1, Position cube2) {
        int totalDifference = difference(cube1.x(), cube2.x()) + difference(cube1.y(), cube2.y()) + difference(cube1.z(), cube2.z());
        if (totalDifference == 0) {
            throw new IllegalArgumentException("Cubes %s and %s overlap".formatted(cube1, cube2));
        }
        return totalDifference == 1;
    }

    private int difference(int coordinate1, int coordinate2) {
        return Math.abs(coordinate2 - coordinate1);
    }

    private record Range3d(Range xRange, Range yRange, Range zRange) {

        public boolean isInLimit(Position position) {
            return xRange.isInLimit(position.x()) || yRange.isInLimit(position.y()) || zRange.isInLimit(position.z());
        }
    }

    private record Range(int min, int max) {

        public static Range from(IntSummaryStatistics stats) {
            return new Range(stats.getMin(), stats.getMax());
        }

        public IntStream stream() {
            return IntStream.rangeClosed(min, max);
        }

        public boolean isInLimit(int value) {
            return value == min || value == max;
        }
    }
}
