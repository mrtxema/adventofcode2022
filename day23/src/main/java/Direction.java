import java.util.stream.Stream;

public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public static Stream<Direction> getAllFrom(int initialIndex) {
        return Stream.of(NORTH, SOUTH, WEST, EAST, NORTH, SOUTH, WEST).skip(initialIndex).limit(4);
    }
}
