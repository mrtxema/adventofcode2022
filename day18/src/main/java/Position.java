import java.util.Arrays;
import java.util.stream.Stream;

public record Position(int x, int y, int z) {

    public static Position parse(String spec) {
        int[] coordinates = Arrays.stream(spec.split(",", 3)).mapToInt(Integer::parseInt).toArray();
        if (coordinates.length != 3) {
            throw new IllegalArgumentException("Invalid position: " + spec);
        }
        return new Position(coordinates[0], coordinates[1], coordinates[2]);
    }

    public Stream<Position> getAllAdjacents() {
        return Stream.of(
                new Position(x - 1, y, z),
                new Position(x + 1, y, z),
                new Position(x, y - 1, z),
                new Position(x, y + 1, z),
                new Position(x, y, z - 1),
                new Position(x, y, z + 1));
    }
}
