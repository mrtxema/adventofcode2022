import java.util.Map;
import java.util.Set;

public class ConstantExpression implements Expression {
    private final long value;

    public ConstantExpression(long value) {
        this.value = value;
    }

    @Override
    public long calculateValue(Map<String, Long> dependencyValues) {
        return value;
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
    public String toString() {
        return String.valueOf(value);
    }

    public long getValue() {
        return value;
    }
}
