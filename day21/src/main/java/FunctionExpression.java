import java.util.Map;
import java.util.Set;

public class FunctionExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    private final Operation operation;

    public FunctionExpression(Expression expression1, Expression expression2, Operation operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public long calculateValue(Map<String, Long> dependencyValues) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Expression buildExpression(Map<String, Expression> dependencyExpressions) {
        return this;
    }

    @Override
    public Set<String> getDependencyMonkeyIds() {
        return Set.of();
    }

    @Override
    public boolean includes(Expression expression) {
        return expression1.includes(expression) || expression2.includes(expression);
    }

    @Override
    public String toString() {
        return "(%s %s %s)".formatted(expression1, operation.getSymbol(), expression2);
    }

    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public Operation getOperation() {
        return operation;
    }

    public Expression simplify() {
        if ((expression1 instanceof ConstantExpression constant1) && (expression2 instanceof ConstantExpression constant2)) {
            return new ConstantExpression(calculate(constant1.getValue(), constant2.getValue()));
        }
        return this;
    }

    private long calculate(long value1, long value2) {
        return switch (operation) {
            case ADD -> value1 + value2;
            case SUBSTRACT -> value1 - value2;
            case MULTIPLY -> value1 * value2;
            case DIVIDE -> value1 / value2;
        };
    }
}
