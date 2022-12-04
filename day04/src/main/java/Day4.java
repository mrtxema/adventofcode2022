import java.io.IOException;
import java.util.List;

public class Day4 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day4().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        AssignmentService assignmentService = new AssignmentService();
        List<AssignmentPair> assignmentPairs = readInputFile(INPUT_FILE_NAME);
        new Part1(assignmentService).run(assignmentPairs);
        new Part2(assignmentService).run(assignmentPairs);
    }

    private List<AssignmentPair> readInputFile(String fileName) {
        try {
            return IOUtils.readLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(AssignmentPair::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
