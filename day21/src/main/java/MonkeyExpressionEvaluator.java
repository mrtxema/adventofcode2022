import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MonkeyExpressionEvaluator {
    private final Map<String, Monkey> monkeys;
    private final Map<String, Long> valueCache;
    private final Map<String, Long> valuesView;
    private final Map<String, Expression> expressionCache;
    private final Map<String, Expression> expressionsView;

    public MonkeyExpressionEvaluator(List<Monkey> monkeys) {
        this.monkeys = monkeys.stream().collect(Collectors.toMap(Monkey::id, Function.identity()));
        this.valueCache = new ConcurrentSkipListMap<>();
        this.valuesView = Collections.unmodifiableMap(this.valueCache);
        this.expressionCache = new ConcurrentSkipListMap<>();
        this.expressionsView = Collections.unmodifiableMap(this.expressionCache);
    }

    public long evaluate(String monkeyId) {
        return valueCache.computeIfAbsent(monkeyId, this::calculate);
    }

    private long calculate(String monkeyId) {
        Monkey monkey = monkeys.get(monkeyId);
        monkey.expression().getDependencyMonkeyIds().forEach(this::evaluate);
        return monkey.expression().calculateValue(valuesView);
    }

    public long calculateMyNumber(String myId, String equationMonkeyId) {
        monkeys.put(myId, new Monkey(myId, Equation.UNKNOWN));
        List<String> equationIds = getEquationIds(monkeys.get(equationMonkeyId));
        Equation equation = new Equation(getExpression(equationIds.get(0)), getExpression(equationIds.get(1)));
        return equation.solve();
    }

    private Expression getExpression(String monkeyId) {
        return expressionCache.computeIfAbsent(monkeyId, this::buildExpression);
    }

    private Expression buildExpression(String monkeyId) {
        Monkey monkey = monkeys.get(monkeyId);
        monkey.expression().getDependencyMonkeyIds().forEach(this::getExpression);
        return monkey.expression().buildExpression(expressionsView);
    }

    private List<String> getEquationIds(Monkey monkey) {
        if (! (monkey.expression() instanceof Monkey.MonkeyFunctionExpression function)) {
            throw new IllegalArgumentException("Monkey %s doesn't define a function expression".formatted(monkey.id()));
        }
        return new ArrayList<>(function.getDependencyMonkeyIds());
    }
}
