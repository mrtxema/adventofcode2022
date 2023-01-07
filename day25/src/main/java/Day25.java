import java.io.IOException;
import java.util.List;

public class Day25 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day25().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<SnafuNumber> fuelRequirements = readInputFile(INPUT_FILE_NAME);
        long totalFuel = fuelRequirements.stream().mapToLong(SnafuNumber::longValue).sum();
        System.out.println("SNAFU total fuel: " + SnafuNumber.fromLong(totalFuel));
    }

    private List<SnafuNumber> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isBlank())
                    .map(SnafuNumber::valueOf)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
