import java.util.stream.IntStream;

public class Crane9000 extends BaseCrane {

    @Override
    protected void applyRearrangement(CrateStacks stacks, Rearrangement rearrangement) {
        IntStream.range(0, rearrangement.numCrates())
                .forEach(i -> applyRearrangement(stacks, rearrangement.sourceStackId(), rearrangement.targetStackId()));
    }

    private void applyRearrangement(CrateStacks stacks, String sourceStackId, String targetStackId) {
        Character crate = stacks.get(sourceStackId).removeFirst();
        stacks.get(targetStackId).addFirst(crate);
    }
}
