import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monkey {
    private final int id;
    private final List<Long> startingItems;
    private final List<Long> items;
    private final Operation operation;
    private final long divisibilityFactor;
    private final int whenTrueMonkeyId;
    private final int whenFalseMonkeyId;
    private long inspectionCounter;

    public Monkey(int id, List<Long> items, Operation operation, long divisibilityFactor, int whenTrueMonkeyId, int whenFalseMonkeyId) {
        this.id = id;
        this.startingItems = items;
        this.items = new ArrayList<>();
        this.operation = operation;
        this.divisibilityFactor = divisibilityFactor;
        this.whenTrueMonkeyId = whenTrueMonkeyId;
        this.whenFalseMonkeyId = whenFalseMonkeyId;
    }

    public void reset() {
        items.clear();
        items.addAll(startingItems);
        inspectionCounter = 0;
    }

    public int getId() {
        return id;
    }

    public List<Long> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(long item) {
        items.add(item);
    }

    public long applyOperation(long item) {
        inspectionCounter++;
        return operation.apply(item);
    }

    public int getTargetMonkeyId(long item) {
        return item % divisibilityFactor == 0L ? whenTrueMonkeyId : whenFalseMonkeyId;
    }

    public long getDivisibilityFactor() {
        return divisibilityFactor;
    }

    public long countInspections() {
        return inspectionCounter;
    }
}
