import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MonkeyParser {
    private static final Pattern MONKEY_NUMBER_PATTERN = Pattern.compile("Monkey ([0-9]+):");
    private static final String STARTING_ITEMS_PREFIX = "Starting items:";
    private static final String OPERATION_PREFIX = "Operation: new =";
    private static final Pattern TEST_PATTERN = Pattern.compile("Test: divisible by ([0-9]+)");
    private static final Pattern CONDITION_PATTERN = Pattern.compile("If (true|false): throw to monkey ([0-9]+)");

    public List<Monkey> parse(List<String> lines) {
        int numMonkeys = (lines.size() + 1) / 7;
        return IntStream.range(0, numMonkeys)
                .map(i -> i * 7)
                .mapToObj(index -> lines.subList(index, index + 6))
                .map(this::parseMonkey)
                .toList();
    }

    private Monkey parseMonkey(List<String> lines) {
        return new Monkey(
                parseId(lines.get(0)),
                parseItems(lines.get(1)),
                parseOperation(lines.get(2)),
                parseDivisibilityFactor(lines.get(3)),
                parseCondition(lines.get(4), true),
                parseCondition(lines.get(5), false));
    }

    private int parseId(String spec) {
        Matcher matcher = MONKEY_NUMBER_PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid monkey id line: " + spec);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private List<Long> parseItems(String spec) {
        if (!spec.startsWith(STARTING_ITEMS_PREFIX)) {
            throw new IllegalArgumentException("Invalid starting items line: " + spec);
        }
        return Arrays.stream(spec.substring(STARTING_ITEMS_PREFIX.length()).split(",")).map(String::trim).map(Long::valueOf).toList();
    }

    private Operation parseOperation(String spec) {
        if (!spec.startsWith(OPERATION_PREFIX)) {
            throw new IllegalArgumentException("Invalid operation line: " + spec);
        }
        String[] parts = spec.substring(OPERATION_PREFIX.length()).trim().split(" +", 3);
        return new Operation(parts[0], parts[1], parts[2]);
    }

    private long parseDivisibilityFactor(String spec) {
        Matcher matcher = TEST_PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid test line: " + spec);
        }
        return Long.parseLong(matcher.group(1));
    }

    private int parseCondition(String spec, boolean expectedCondition) {
        Matcher matcher = CONDITION_PATTERN.matcher(spec);
        if (!matcher.matches() || !matcher.group(1).equals(String.valueOf(expectedCondition))) {
            throw new IllegalArgumentException("Invalid condition line: " + spec);
        }
        return Integer.parseInt(matcher.group(2));
    }
}
