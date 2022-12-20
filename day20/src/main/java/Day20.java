import java.io.IOException;
import java.util.List;

public class Day20 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day20().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Integer> encryptedCoordinates = readInputFile(INPUT_FILE_NAME);
        long coordinateSum1 = CoordinateDecrypter.newVariant1(encryptedCoordinates).decrypt().sum();
        System.out.println("Part 1. The coordinate sum is: " + coordinateSum1);
        long coordinateSum2 = CoordinateDecrypter.newVariant2(encryptedCoordinates).decrypt().sum();
        System.out.println("Part 2. The coordinate sum is: " + coordinateSum2);
    }

    private List<Integer> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Integer::valueOf)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
