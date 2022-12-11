public record Movement(Direction direction, int steps) {

    public static Movement parse(String spec) {
        String[] parts = spec.split(" +", 2);
        return new Movement(Direction.parse(parts[0]), Integer.parseInt(parts[1]));
    }
}
