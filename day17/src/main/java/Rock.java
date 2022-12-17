import java.util.List;
import java.util.stream.Stream;

public class Rock {
    private static final List<Rock> ALL_ROCKS = buildAllRocks();

    private final List<Position> positions;

    private Rock(List<Position> positions) {
        this.positions = positions;
    }

    private static List<Rock> buildAllRocks() {
        return List.of(
                new Rock(List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0))),
                new Rock(List.of(new Position(1, 0), new Position(0, 1), new Position(1, 1), new Position(2, 1), new Position(1, 2))),
                new Rock(List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(2, 1), new Position(2, 2))),
                new Rock(List.of(new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(0, 3))),
                new Rock(List.of(new Position(0, 0), new Position(1, 0), new Position(0, 1), new Position(1, 1))));
    }

    public static List<Rock> getAllRocks() {
        return ALL_ROCKS;
    }

    public Stream<Position> place(Position position) {
        return positions.stream().map(p -> p.move(position.x(), position.y()));
    }
}
