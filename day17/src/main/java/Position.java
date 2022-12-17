public record Position(int x, int y) {

    public Position move(int offsetX, int offsetY) {
        return new Position(x + offsetX, y + offsetY);
    }
}
