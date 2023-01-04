import java.io.IOException;
import java.util.List;

public class Day24 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day24().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        BlizzardsMap map = new BlizzardsMapParser().parse(readInputFile(INPUT_FILE_NAME));
        BlizzardsMapEvaluator evaluator = new BlizzardsMapEvaluator(map);
        System.out.println("Part 1. One way shortest path minutes: " + evaluator.evaluateShortestPath());
        System.out.println("Part 2. Three leg shortest path minutes: " + evaluator.evaluateThreeLegShortestPath());
    }

    private List<String> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isBlank())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
