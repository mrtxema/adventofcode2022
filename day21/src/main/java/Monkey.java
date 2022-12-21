import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Monkey(String id, Expression expression) {
    private static final Pattern CONSTANT_MONKEY_PATTERN = Pattern.compile("([a-z]+): ([0-9]+)");
    private static final Pattern FUNCTION_MONKEY_PATTERN = Pattern.compile("([a-z]+): ([a-z]+) ([+-\\\\*/]) ([a-z]+)");

    public static Monkey parse(String spec) {
        Matcher constantMatcher = CONSTANT_MONKEY_PATTERN.matcher(spec);
        if (constantMatcher.matches()) {
            return new Monkey(constantMatcher.group(1), new ConstantExpression(Long.parseLong(constantMatcher.group(2))));
        }
        Matcher functionMatcher = FUNCTION_MONKEY_PATTERN.matcher(spec);
        if (!functionMatcher.matches()) {
            throw new IllegalArgumentException("Invalid monkey spec: " + spec);
        }
        var func = new MonkeyFunctionExpression(functionMatcher.group(2), functionMatcher.group(4), Operation.parse(functionMatcher.group(3)));
        return new Monkey(functionMatcher.group(1), func);
    }

    static class MonkeyFunctionExpression implements Expression {
        private final String monkeyId1;
        private final String monkeyId2;
        private final Operation operation;

        public MonkeyFunctionExpression(String monkeyId1, String monkeyId2, Operation operation) {
            this.monkeyId1 = monkeyId1;
            this.monkeyId2 = monkeyId2;
            this.operation = operation;
        }

        @Override
        public long calculateValue(Map<String, Long> dependencyValues) {
            long value1 = dependencyValues.get(monkeyId1);
            long value2 = dependencyValues.get(monkeyId2);
            return switch (operation) {
                case ADD -> value1 + value2;
                case SUBSTRACT -> value1 - value2;
                case MULTIPLY -> value1 * value2;
                case DIVIDE -> value1 / value2;
            };
        }

        @Override
        public Expression buildExpression(Map<String, Expression> dependencyExpressions) {
            Expression expression1 = dependencyExpressions.get(monkeyId1);
            Expression expression2 = dependencyExpressions.get(monkeyId2);
            return new FunctionExpression(expression1, expression2, operation).simplify();
        }

        @Override
        public Set<String> getDependencyMonkeyIds() {
            return Set.of(monkeyId1, monkeyId2);
        }
    }
}
