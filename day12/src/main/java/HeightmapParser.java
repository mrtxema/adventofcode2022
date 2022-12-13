import java.util.List;
import java.util.stream.IntStream;

public class HeightmapParser {

    public Heightmap parse(List<String> lines) {
        Position startPosition = findPosition(lines, 'S');
        Position targetPosition = findPosition(lines, 'E');
        List<String> normalizedRows = lines.stream()
                .map(line -> line.replace('S', 'a'))
                .map(line -> line.replace('E', 'z'))
                .toList();
        return new Heightmap(normalizedRows, startPosition, targetPosition);
    }

    private Position findPosition(List<String> rows, char searchChar) {
        List<Position> foundPositions = IntStream.range(0, rows.size())
                .mapToObj(y -> new Position(rows.get(y).indexOf(searchChar), y))
                .filter(position -> position.x() != -1)
                .toList();
        return switch (foundPositions.size()) {
            case 0 -> throw new IllegalArgumentException("Couldn't find item " + searchChar);
            case 1 -> foundPositions.get(0);
            default -> throw new IllegalArgumentException("Item %s appears %d times".formatted(searchChar, foundPositions.size()));
        };
    }
}
