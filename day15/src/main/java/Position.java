public record Position(int x, int y) {

    public int distance(Position other) {
        return Math.abs(other.x() - x) + Math.abs(other.y() - y);
    }

    public Position moveHorizontally(int horizontalOffset) {
        return new Position(x + horizontalOffset, y);
    }
}
