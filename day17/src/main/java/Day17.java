import java.io.IOException;
import java.util.List;

public class Day17 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day17().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Integer> gasJets = readInputFile(INPUT_FILE_NAME);
        System.out.println("Part 1. Tower height: " + new RockFallSimulator(gasJets).simulateRockFalls(2022).getMaxHeight());
        //System.out.println("Part 1. Optimized   : " + new RockFallSimulator(gasJets).simulateRockFallsOptimized(2022));
        System.out.println("Part 2. Tower height: " + new RockFallSimulator(gasJets).simulateRockFallsOptimized(1_000_000_000_000L));
    }

    private List<Integer> readInputFile(String fileName) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .flatMap(line -> line.chars().boxed())
                    .map(this::parseGasJet)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Integer parseGasJet(int spec) {
        return switch (spec) {
            case '<' -> -1;
            case '>' -> 1;
            default -> throw new IllegalArgumentException("Unexpected gas jet: " + (char) spec);
        };
    }
}
