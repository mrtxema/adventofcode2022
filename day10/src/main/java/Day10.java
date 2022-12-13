import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day10 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day10().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Sentence> sentences = readInputFile(INPUT_FILE_NAME);
        SentenceExecutor executor = new SentenceExecutor().execute(sentences);
        int signalStrengthSum = Stream.of(20, 60, 100, 140, 180, 220).mapToInt(executor::getSignalStrength).sum();
        System.out.println("Part 1. The sum of the signal strengths is: " + signalStrengthSum);
        System.out.printf("Part 2.%n%s%n", executor.drawPixels());
    }

    private List<Sentence> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Sentence::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
