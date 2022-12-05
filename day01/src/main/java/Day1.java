import java.io.IOException;
import java.util.List;

public class Day1 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day1().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<ElfInventory> inventories = readInputFile(INPUT_FILE_NAME);
        new Part1().run(inventories);
        new Part2().run(inventories);
    }

    private List<ElfInventory> readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readTrimmedLines(getClass().getResource(fileName));
            return new InventoryParser().parseInventories(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
