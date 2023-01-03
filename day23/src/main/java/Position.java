import java.util.stream.Stream;

public record Position(int x, int y) {

    public Stream<Position> getAllAdjacents() {
        return Stream.of(
                new Position(x - 1, y - 1), new Position(x, y - 1), new Position(x + 1, y - 1),
                new Position(x - 1, y),                                   new Position(x + 1, y),
                new Position(x - 1, y + 1), new Position(x, y + 1), new Position(x + 1, y + 1));
    }

    public Stream<Position> getAdjacents(Direction direction) {
        return switch (direction) {
            case NORTH -> Stream.of(new Position(x - 1, y - 1), new Position(x, y - 1), new Position(x + 1, y - 1));
            case SOUTH -> Stream.of(new Position(x - 1, y + 1), new Position(x, y + 1), new Position(x + 1, y + 1));
            case WEST -> Stream.of(new Position(x - 1, y - 1), new Position(x - 1, y), new Position(x - 1, y + 1));
            case EAST -> Stream.of(new Position(x + 1, y - 1), new Position(x + 1, y), new Position(x + 1, y + 1));
        };
    }

    public Position move(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(x, y - 1);
            case SOUTH -> new Position(x, y + 1);
            case WEST -> new Position(x - 1, y);
            case EAST -> new Position(x + 1, y);
        };
    }
}
