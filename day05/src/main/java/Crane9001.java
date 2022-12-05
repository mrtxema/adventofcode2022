import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

public class Crane9001 extends BaseCrane {

    @Override
    protected void applyRearrangement(CrateStacks stacks, Rearrangement rearrangement) {
        Deque<Character> sourceStack = stacks.get(rearrangement.sourceStackId());
        Deque<Character> tempStack = new ArrayDeque<>();
        Deque<Character> targetStack = stacks.get(rearrangement.targetStackId());
        applyRearrangement(rearrangement.numCrates(), sourceStack, tempStack);
        applyRearrangement(rearrangement.numCrates(), tempStack, targetStack);
    }

    private void applyRearrangement(int numCrates, Deque<Character> source, Deque<Character> target) {
        IntStream.range(0, numCrates).forEach(i -> target.addFirst(source.removeFirst()));
    }
}
