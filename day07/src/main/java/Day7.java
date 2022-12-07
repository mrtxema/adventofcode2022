import java.io.IOException;
import java.util.List;

public class Day7 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day7().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Directory rootDirectory  = readInputFile(INPUT_FILE_NAME);
        DirectorySizeCalculator calculator = new DirectorySizeCalculator(rootDirectory);
        new Part1(calculator).run();
        new Part2(calculator).run(rootDirectory);
    }

    private Directory readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .toList();
            return new FilesystemContentParser().parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
