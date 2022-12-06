import java.io.IOException;
import java.util.List;

public class Day6 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day6().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<String> lines  = readInputFile(INPUT_FILE_NAME);
        System.out.println("Part 1");
        new MarkerDetector(4).run(lines);
        System.out.println();
        System.out.println("Part 2");
        new MarkerDetector(14).run(lines);
    }

    private List<String> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
