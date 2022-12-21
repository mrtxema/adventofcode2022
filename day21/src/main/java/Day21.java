import java.io.IOException;
import java.util.List;

public class Day21 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day21().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Monkey> monkeys = readInputFile(INPUT_FILE_NAME);
        MonkeyExpressionEvaluator evaluator = new MonkeyExpressionEvaluator(monkeys);
        System.out.println("Part 1. The root monkey yells: " + evaluator.evaluate("root"));
        System.out.println("Part 2. I need to yell: " + evaluator.calculateMyNumber("humn", "root"));
    }

    private List<Monkey> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Monkey::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
