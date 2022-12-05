public abstract class BaseCrane implements Crane {

    @Override
    public CrateStacks applyRearrangements(RearrangementProcedure procedure) {
        CrateStacks stacks = procedure.crateStacks().copy();
        procedure.rearrangements().forEach(rearrangement -> applyRearrangement(stacks, rearrangement));
        return stacks;
    }

    protected abstract void applyRearrangement(CrateStacks stacks, Rearrangement rearrangement);
}
