import java.io.IOException;
import java.util.List;

public class Day19 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day19().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Blueprint> blueprints = readInputFile(INPUT_FILE_NAME);
        int totalQualityLevel = blueprints.stream()
                .map(BlueprintOptimizer::new)
                .mapToInt(optimizer -> optimizer.calculateQualityLevel(24))
                .sum();
        System.out.println("Part 1. The total quality level is: " + totalQualityLevel);
        long maxGeodeMultiplied = blueprints.stream().limit(3)
                .map(BlueprintOptimizer::new)
                .mapToLong(optimizer -> optimizer.maxGeodeProduction(32))
                .reduce(1, (a, b) -> a * b);
        System.out.println("Part 2. Max geode multiplied: " + maxGeodeMultiplied);
    }

    private List<Blueprint> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Blueprint::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
