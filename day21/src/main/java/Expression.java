import java.util.Map;
import java.util.Set;

public interface Expression {

    long calculateValue(Map<String, Long> dependencyValues);

    Expression buildExpression(Map<String, Expression> dependencyExpressions);

    Set<String> getDependencyMonkeyIds();

    default boolean includes(Expression expression) {
        return equals(expression);
    }
}
