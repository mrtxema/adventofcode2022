import java.io.IOException;
import java.util.List;

public class Day3 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day3().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        RucksackService rucksackService = new RucksackService();
        List<Rucksack> rucksacks = readInputFile(INPUT_FILE_NAME);
        new Part1(rucksackService).run(rucksacks);
        new Part2(rucksackService).run(rucksacks);
    }

    private List<Rucksack> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(Rucksack::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
