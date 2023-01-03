import java.io.IOException;
import java.util.List;

public class Day22 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day22().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String fileName = INPUT_FILE_NAME;
        PathEvaluator evaluator = new PathEvaluator(new PathInfoParser().parse(readInputFile(fileName)));
        System.out.println("Part 1. Password: " + evaluator.getPasswordInMap());
        System.out.println("Part 2. Password: " + evaluator.getPasswordInCube(fileName.equals(TEST_FILE_NAME)));
    }

    private List<String> readInputFile(String fileName) {
        try {
            return IOUtils.readLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isBlank())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
