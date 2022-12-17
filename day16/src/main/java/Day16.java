import java.io.IOException;
import java.util.List;

public class Day16 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day16().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<ValveScan> valveScans = readInputFile(INPUT_FILE_NAME);
        long maxPressureRelease = new RoutePlanner(valveScans).findMaxPressureRelease();
        System.out.println("Part 1. Max pressure release: " + maxPressureRelease);
        long maxPressureReleaseWithElephant = new RoutePlannerWithElephant(valveScans).findMaxPressureRelease();
        System.out.println("Part 2. Max pressure release: " + maxPressureReleaseWithElephant);
    }

    private List<ValveScan> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(ValveScan::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
