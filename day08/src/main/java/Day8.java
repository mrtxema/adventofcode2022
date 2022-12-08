import java.io.IOException;
import java.util.List;

public class Day8 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day8().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        TreeGrid treeGrid  = readInputFile(INPUT_FILE_NAME);
        System.out.println("Part 1. The number of visible trees is: " + treeGrid.countVisibleTrees());
        System.out.println("Part 2. The highest scenic score is:    " + treeGrid.getHighestScenicScore());
    }

    private TreeGrid readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .toList();
            return new TreeGridParser().parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
