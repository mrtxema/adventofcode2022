import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Heightmap {
    private final List<String> rows;
    private final Position startPosition;
    private final Position targetPosition;

    public Heightmap(List<String> rows, Position startPosition, Position targetPosition) {
        this.rows = rows;
        this.startPosition = startPosition;
        this.targetPosition = targetPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public int width() {
        return rows.get(0).length();
    }

    public int height() {
        return rows.size();
    }

    public int getElevation(Position position) {
        return rows.get(position.y()).charAt(position.x()) - 'a';
    }

    public List<Position> getAllPositionsWithElevation(int elevation) {
        return getAllPositions().filter(position -> getElevation(position) == elevation).toList();
    }

    private Stream<Position> getAllPositions() {
        return IntStream.range(0, height())
                .boxed()
                .flatMap(y -> IntStream.range(0, width()).mapToObj(x -> new Position(x, y)));
    }
}
