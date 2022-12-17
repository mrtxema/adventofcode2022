import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chamber {
    private static final int WIDTH = 7;
    private final List<BitSet> rows = new ArrayList<>();

    public int getHeight() {
        return rows.size();
    }

    public boolean collides(Stream<Position> positions) {
        return !positions.allMatch(this::validate);
    }

    public void putItem(Stream<Position> positions) {
        positions.forEach(this::put);
    }

    private void put(Position position) {
        if (!validate(position)) {
            throw new IllegalStateException("Invalid position: " + position);
        }
        createRowIfNeeded(position.y());
        BitSet row = rows.get(position.y());
        row.set(position.x());
    }

    private boolean validate(Position position) {
        if (position.x() < 0 || position.x() >= WIDTH || position.y() < 0) {
            return false;
        }
        if (position.y() >= rows.size()) {
            return true;
        }
        return !rows.get(position.y()).get(position.x());
    }

    private void createRowIfNeeded(int y) {
        while (rows.size() <= y) {
            rows.add(newRow());
        }
    }

    private BitSet newRow() {
        return new BitSet(WIDTH);
    }

    public Stream<String> draw() {
        return IntStream.range(0, rows.size())
                .map(i -> rows.size() - i - 1)
                .mapToObj(rows::get)
                .map(this::drawRow);
    }

    private String drawRow(BitSet bitSet) {
        return IntStream.range(0, WIDTH).mapToObj(bitSet::get).map(occupied -> occupied ? "#" : ".").collect(Collectors.joining());
    }

    public List<Integer> getSkyline() {
        return IntStream.range(0, WIDTH).map(this::getColumnDepth).boxed().toList();
    }

    private int getColumnDepth(int column) {
        int baseRow = rows.size() - 1;
        while (baseRow >= 0 && !rows.get(baseRow).get(column)) {
            baseRow--;
        }
        return (rows.size() - 1) - baseRow;
    }
}
