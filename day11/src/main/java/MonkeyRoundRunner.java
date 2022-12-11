import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyRoundRunner {
    private final long lcm;
    private final Function<Long, Long> worryLevelRelief;

    public MonkeyRoundRunner(long lcm, Function<Long, Long> worryLevelRelief) {
        this.lcm = lcm;
        this.worryLevelRelief = worryLevelRelief;
    }

    public long runRounds(List<Monkey> monkeyList, int numRounds) {
        monkeyList.forEach(Monkey::reset);
        SortedMap<Integer, Monkey> monkeys = monkeyList.stream()
                .collect(Collectors.toMap(Monkey::getId, Function.identity(), (a, b) -> a, TreeMap::new));
        IntStream.range(0, numRounds).forEach(round -> processRound(monkeys));
        return monkeyList.stream()
                .sorted(Comparator.comparing(Monkey::countInspections).reversed())
                .limit(2)
                .mapToLong(Monkey::countInspections)
                .reduce(1, (a, b) -> a * b);
    }

    private void processRound(SortedMap<Integer, Monkey> monkeys) {
        monkeys.forEach((id, monkey) -> processTurn(monkey, monkeys));
    }

    private void processTurn(Monkey monkey, SortedMap<Integer, Monkey> monkeys) {
        monkey.getItems().forEach(item -> processItem(monkey, item, monkeys));
        monkey.clearItems();
    }

    private void processItem(Monkey monkey, long item, SortedMap<Integer, Monkey> monkeys) {
        long worryLevel = worryLevelRelief.apply(monkey.applyOperation(item));
        int targetMonkeyId = monkey.getTargetMonkeyId(worryLevel);
        if (targetMonkeyId == monkey.getId()) {
            throw new IllegalStateException("Can't throw item to itself (monkey:%d, item:%d)".formatted(monkey.getId(), item));
        }
        monkeys.get(targetMonkeyId).addItem(worryLevel % lcm);
    }
}
