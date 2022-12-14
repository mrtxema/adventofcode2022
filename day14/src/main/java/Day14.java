import java.io.IOException;
import java.util.List;

public class Day14 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day14().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<PathScan> pathScans = readInputFile(INPUT_FILE_NAME);
        MapRenderer mapRenderer = new MapRenderer(pathScans, new Position(500, 0));
        int sandUnits = mapRenderer.simulateSandFall().countSandUnits();
        System.out.println("Part 1. Sand units count: " + sandUnits);
        int floorSandUnits = mapRenderer.addFloor().simulateSandFall().countSandUnits();
        System.out.println("Part 2. Sand units count with floor: " + floorSandUnits);
    }

    private List<PathScan> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(PathScan::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
