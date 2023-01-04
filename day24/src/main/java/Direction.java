import java.util.Arrays;

public enum Direction {
    NORTH('^'),
    SOUTH('v'),
    WEST('<'),
    EAST('>');

    private final char code;

    Direction(char code) {
        this.code = code;
    }

    public static Direction parse(char code) {
        return Arrays.stream(Direction.values()).filter(direction -> direction.code == code).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid blizzard symbol: " + code));
    }
}
