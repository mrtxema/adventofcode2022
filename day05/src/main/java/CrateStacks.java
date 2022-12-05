import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrateStacks {
    private final Map<String, Deque<Character>> stacks;

    public CrateStacks(Map<String, Deque<Character>> stacks) {
        this.stacks = new HashMap<>(stacks);
    }

    public Deque<Character> get(String id) {
        return stacks.get(id);
    }

    public List<Character> getTopCrates() {
        return IntStream.rangeClosed(1, stacks.size())
                .mapToObj(String::valueOf)
                .map(stacks::get)
                .filter(stack -> !stack.isEmpty())
                .map(Deque::getFirst)
                .toList();
    }

    public CrateStacks copy() {
        Map<String, Deque<Character>> clonedStacks = stacks.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayDeque<>(entry.getValue())));
        return new CrateStacks(clonedStacks);
    }
}
