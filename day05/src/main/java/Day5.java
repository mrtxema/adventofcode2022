import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day5().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        RearrangementProcedure procedure = readInputFile(INPUT_FILE_NAME);
        run(1, new Crane9000(), procedure);
        run(2, new Crane9001(), procedure);
    }

    public void run(int part, Crane crane, RearrangementProcedure procedure) {
        CrateStacks stacks = crane.applyRearrangements(procedure);
        String result = stacks.getTopCrates().stream().map(String::valueOf).collect(Collectors.joining());
        System.out.printf("Part %d. Crates on top are: %s%n", part, result);
    }

    private RearrangementProcedure readInputFile(String fileName) {
        try {
            List<String> lines = IOUtils.readLines(getClass().getResource(fileName));
            return new RearrangementProcedureParser().parse(lines);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
