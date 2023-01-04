public record Blizzard(Position position, Direction direction) {

    public Blizzard move(int width, int height) {
        Position newPosition = position.move(direction);
        if (newPosition.x() == 0) {
            newPosition = new Position(width - 2, newPosition.y());
        }
        if (newPosition.x() == width - 1) {
            newPosition = new Position(1, newPosition.y());
        }
        if (newPosition.y() == 0) {
            newPosition = new Position(newPosition.x(), height - 2);
        }
        if (newPosition.y() == height - 1) {
            newPosition = new Position(newPosition.x(), 1);
        }
        return new Blizzard(newPosition, direction);
    }
}
