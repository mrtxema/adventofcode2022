import java.util.Arrays;

enum Operation {
    ADD("+", true),
    SUBSTRACT("-", false),
    MULTIPLY("*", true),
    DIVIDE("/", false);

    private final String symbol;
    private final boolean commutative;

    Operation(String symbol, boolean commutative) {
        this.symbol = symbol;
        this.commutative = commutative;
    }

    public static Operation parse(String spec) {
        return Arrays.stream(Operation.values()).filter(operation -> operation.getSymbol().equals(spec)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation: " + spec));
    }

    public String getSymbol() {
        return symbol;
    }

    public Operation opposite() {
        return switch (this) {
            case ADD -> SUBSTRACT;
            case SUBSTRACT -> ADD;
            case MULTIPLY -> DIVIDE;
            case DIVIDE -> MULTIPLY;
        };
    }

    public boolean isCommutative() {
        return commutative;
    }
}
