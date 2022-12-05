import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RearrangementProcedureParser {
    private static final String EMPTY_STRING = "";

    public RearrangementProcedure parse(List<String> lines) {
        int index = lines.indexOf(EMPTY_STRING);
        if (index == -1) {
            throw new IllegalArgumentException("Missing separator blank line");
        }
        return new RearrangementProcedure(parseStacks(lines.subList(0, index)), parseRearrangements(lines.subList(index, lines.size())));
    }

    private CrateStacks parseStacks(List<String> lines) {
        String[] stackIds = parseStackIds(lines.get(lines.size() - 1));
        List<ArrayDeque<Character>> stacks = IntStream.range(0, stackIds.length).mapToObj(i -> new ArrayDeque<Character>()).toList();
        for (int i = lines.size() - 2; i >= 0; i--) {
            parseCrates(lines.get(i)).forEach((index, crate) -> stacks.get(index).addFirst(crate));
        }
        Map<String, Deque<Character>> stacksById = new HashMap<>();
        for (int i = 0; i < stackIds.length; i++) {
            stacksById.put(stackIds[i], stacks.get(i));
        }
        return new CrateStacks(stacksById);
    }

    private String[] parseStackIds(String spec) {
        return spec.trim().split(" +");
    }

    private Map<Integer, Character> parseCrates(String spec) {
        Map<Integer, Character> crates = new HashMap<>();
        for (int i = 1; i < spec.length(); i += 4) {
            if (Character.isAlphabetic(spec.charAt(i))) {
                crates.put((i - 1) / 4, spec.charAt(i));
            }
        }
        return crates;
    }

    private List<Rearrangement> parseRearrangements(List<String> lines) {
        return lines.stream().map(String::trim).filter(line -> !line.isEmpty()).map(Rearrangement::parse).toList();
    }
}
