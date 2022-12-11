public record Operation(String operand1, String operation, String operand2) {
    private static final String OLD_VARIABLE = "old";

    public long apply(long item) {
        return applyOperation(getOperand(operand1, item), getOperand(operand2, item));
    }

    private long getOperand(String operandSpec, long oldValue) {
        return operandSpec.equals(OLD_VARIABLE) ? oldValue : Long.parseLong(operandSpec);
    }

    private long applyOperation(long operand1, long operand2) {
        return switch (operation) {
            case "*" -> operand1 * operand2;
            case "+" -> operand1 + operand2;
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };
    }
}
