import java.util.Arrays;

public enum Direction {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    private final String code;

    Direction(String code) {
        this.code = code;
    }

    public static Direction parse(String spec) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.code.equals(spec))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Unknown direction: " + spec));
    }
}
