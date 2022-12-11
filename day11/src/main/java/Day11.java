import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class Day11 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day11().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Monkey> monkeys = readInputFile(INPUT_FILE_NAME);
        long lcm = monkeys.stream().mapToLong(Monkey::getDivisibilityFactor).reduce(1, (a, b) -> a * b);
        System.out.println("Part 1. The monkey business level is: " +
                new MonkeyRoundRunner(lcm, level -> level / 3).runRounds(monkeys, 20));
        System.out.println("Part 2. The monkey business level is: " +
                new MonkeyRoundRunner(lcm, Function.identity()).runRounds(monkeys, 10_000));
    }

    private List<Monkey> readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(fileName));
            return new MonkeyParser().parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
