import java.util.Map;
import java.util.Set;

public class Equation {
    public static final Expression UNKNOWN = new Expression() {
        @Override
        public long calculateValue(Map<String, Long> dependencyValues) {
            return 0;
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
            return "x";
        }
    };
    private final Expression expression1;
    private final Expression expression2;

    public Equation(Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public long solve() {
        Equation equation = this;
        while (!equation.isTrivial()) {
            Equation previous = equation;
            equation = equation.simplifyExpression1();
            if (equation.equals(previous)) {
                equation = equation.simplifyExpression2();
            }
            equation = equation.reverseIfNeeded();
            if (equation.equals(previous)) {
                throw new IllegalStateException("Can't simplify more");
            }
        }
        if (equation.expression1 != UNKNOWN || !(equation.expression2 instanceof ConstantExpression constant)) {
            throw new IllegalStateException("Illegal trivial step: %s = %s".formatted(equation.expression1, equation.expression2));
        }
        return constant.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Equation other && expression1.equals(other.expression1) && expression2.equals(other.expression2);
    }

    private Equation reverseIfNeeded() {
        return expression2.includes(UNKNOWN) && !expression1.includes(UNKNOWN) ? new Equation(expression2, expression1) : this;
    }

    private Equation simplifyExpression1() {
        if (expression1 instanceof FunctionExpression function) {
            if (function.getExpression1() instanceof ConstantExpression && function.getOperation().isCommutative()) {
                Operation newOperator = function.getOperation().opposite();
                Expression newExpression2 = new FunctionExpression(expression2, function.getExpression1(), newOperator).simplify();
                return new Equation(function.getExpression2(), newExpression2);
            } else if (function.getExpression1() instanceof ConstantExpression || function.getExpression2() instanceof ConstantExpression) {
                Operation newOperator = function.getOperation().opposite();
                Expression newExpression2 = new FunctionExpression(expression2, function.getExpression2(), newOperator).simplify();
                return new Equation(function.getExpression1(), newExpression2);
            }
        }
        return this;
    }

    private Equation simplifyExpression2() {
        if (expression2 instanceof FunctionExpression function) {
            if (function.getExpression1().includes(UNKNOWN) && function.getOperation().isCommutative()) {
                Operation newOperator = function.getOperation().opposite();
                Expression newExpression1 = new FunctionExpression(expression1, function.getExpression1(), newOperator).simplify();
                return new Equation(newExpression1, function.getExpression2());
            } else if (function.getExpression2().includes(UNKNOWN)) {
                Operation newOperator = function.getOperation().opposite();
                Expression newExpression1 = new FunctionExpression(expression1, function.getExpression2(), newOperator).simplify();
                return new Equation(newExpression1, function.getExpression1());
            }
        }
        return this;
    }

    private boolean isTrivial() {
        return expression1.equals(UNKNOWN) && expression2 instanceof ConstantExpression;
    }

    @Override
    public String toString() {
        return "%s = %s".formatted(expression1, expression2);
    }
}
