import java.io.IOException;
import java.util.List;

public class Day18 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day18().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Position> cubePositions = readInputFile(INPUT_FILE_NAME);
        SurfaceAreaAnalyzer analyzer = new SurfaceAreaAnalyzer();
        System.out.println("Part 1. Surface area: " + analyzer.calculateSurfaceArea(cubePositions));
        System.out.println("Part 2. Outside surface area: " + analyzer.calculateOutsideSurfaceArea(cubePositions));
    }

    private List<Position> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Position::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
