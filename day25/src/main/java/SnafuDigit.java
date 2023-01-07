import java.util.Arrays;
import java.util.function.Predicate;

public enum SnafuDigit {
    MINUS_TWO('=', -2),
    MINUS_ONE('-', -1),
    ZERO('0', 0),
    ONE('1', 1),
    TWO('2', 2);

    static final int SNAFU_BASE = 5;
    private final char symbol;
    private final int value;

    SnafuDigit(char symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public static SnafuDigit parse(char c) {
        return findAny(digit -> digit.getSymbol() == c, "Invalid digit: " + c);
    }

    public static SnafuDigit fromCongruentValue(int value) {
        return findAny(digit -> modulus(digit.getValue()) == modulus(value), "Invalid congruent value: " + value);
    }

    private static int modulus(int value) {
        return (value + SNAFU_BASE) % SNAFU_BASE;
    }

    private static SnafuDigit findAny(Predicate<SnafuDigit> filter, String errorMessage) {
        return Arrays.stream(SnafuDigit.values()).filter(filter).findAny().orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    public char getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }
}
