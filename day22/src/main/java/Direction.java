public enum Direction {
    UP(3),
    DOWN(1),
    LEFT(2),
    RIGHT(0);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Direction rotate(boolean clockwise) {
        return switch (this) {
            case UP -> clockwise ? RIGHT : LEFT;
            case DOWN -> clockwise ? LEFT : RIGHT;
            case LEFT -> clockwise ? UP : DOWN;
            case RIGHT -> clockwise ? DOWN : UP;
        };
    }
}
