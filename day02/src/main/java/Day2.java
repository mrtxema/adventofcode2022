import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class Day2 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day2().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String fileName = INPUT_FILE_NAME;
        RoundParser parser = new RoundParser();
        new Part(1).run(readInputFile(fileName, parser::parseAsShapePair));
        new Part(2).run(readInputFile(fileName, parser::parseAsShapeAndOutcome));
    }

    private List<Round> readInputFile(String fileName, Function<String, Round> roundParser) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(roundParser)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
