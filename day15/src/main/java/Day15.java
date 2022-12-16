import java.io.IOException;
import java.util.List;

public class Day15 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day15().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String fileName = INPUT_FILE_NAME;
        int row = fileName.equals(INPUT_FILE_NAME) ? 2_000_000 : 10;
        int maxCoordinate = fileName.equals(INPUT_FILE_NAME) ? 4_000_000 : 20;

        List<SensorResponse> sensorResponses = readInputFile(fileName);

        int forbiddenPositions = new MapRowRenderer(row).processResponses(sensorResponses).countFreeForbiddenPositions();
        System.out.printf("Part 1. Forbidden beacon positions at row %d: %d%n", row, forbiddenPositions);

        Position freePosition = new MapRenderer(maxCoordinate).findFreePosition(sensorResponses);
        System.out.println("Part 2. Free position tuning frequency: " + getTuningFrequency(freePosition));
    }

    private long getTuningFrequency(Position position) {
        return position.x() * 4_000_000L + position.y();
    }

    private List<SensorResponse> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(SensorResponse::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
