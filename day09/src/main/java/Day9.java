import java.io.IOException;
import java.util.List;

public class Day9 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test2.txt";

    public static void main(String[] args) {
        try {
            new Day9().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Movement> movements = readInputFile(INPUT_FILE_NAME);
        System.out.println("Part 1. Visited tail positions: " +
                new MovementExecutor(2).applyMovements(movements).countVisitedTailPositions());
        System.out.println("Part 2. Visited tail positions: " +
                new MovementExecutor(10).applyMovements(movements).countVisitedTailPositions());
    }

    private List<Movement> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Movement::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
