public record Position(int x, int y) {

    public static Position parse(String spec) {
        String[] parts = spec.split(",", 2);
        return new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public Position move(Movement movement) {
        return new Position(x + movement.xOffset(), y + movement.yOffset());
    }
}
