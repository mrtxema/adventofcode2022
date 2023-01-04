import java.io.IOException;
import java.util.List;

public class Day23 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day23().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        ElvesMap map = new ElvesMapParser().parse(readInputFile(INPUT_FILE_NAME));
        ElvesMapEvaluator evaluator = new ElvesMapEvaluator(map);
        System.out.println("Part 1. Empty tiles: " + evaluator.evaluate(10).countEmptyTiles());
        System.out.println("Part 2. Rounds: " + evaluator.countRoundsUntilNoMoves());
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
