import java.util.List;

public class Board {
    private final List<String> rows;

    public Board(List<String> rows) {
        this.rows = rows;
    }

    public Position getInitialPosition() {
        int row = 1;
        return new Position(getRow(row).indexOf('.') + 1, row);
    }

    public boolean isWall(Position position) {
        return getTile(position) == '#';
    }

    public boolean isOutOfBounds(Position position) {
        return getTile(position) == ' ';
    }

    public Position wrap(Position position, Direction direction) {
        Position wrapPosition = switch (direction) {
            case UP -> new Position(position.x(), rows.size());
            case DOWN -> new Position(position.x(), 1);
            case LEFT -> new Position(getRow(position.y()).length(), position.y());
            case RIGHT -> new Position(1, position.y());
        };
        while (isOutOfBounds(wrapPosition)) {
            wrapPosition = wrapPosition.move(direction);
        }
        return wrapPosition;
    }

    private char getTile(Position position) {
        String row = getRow(position.y());
        if (position.x() < 1 || position.x() > row.length()) {
            return ' ';
        }
        return row.charAt(position.x() - 1);
    }

    private String getRow(int number) {
        if (number < 1 || number > rows.size()) {
            return "";
        }
        return rows.get(number - 1);
    }
}
