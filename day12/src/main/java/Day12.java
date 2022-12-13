import java.io.IOException;
import java.util.List;

public class Day12 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day12().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Heightmap heightmap = readInputFile(INPUT_FILE_NAME);
        RoutePlanner routePlanner = new RoutePlanner(heightmap);
        routePlanner.calculateDistances();
        System.out.printf("Part 1. The shortest route has %d steps%n", routePlanner.findShortestRouteLengthFromStart());
        System.out.printf("Part 2. The shortest route from any low position has %d steps%n", routePlanner.findShortestRouteLengthFromAny());
    }

    private Heightmap readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .toList();
            return new HeightmapParser().parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
