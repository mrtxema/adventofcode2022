import java.util.Iterator;
import java.util.List;

public class CyclicIterator<T> implements Iterator<T> {
    private final List<T> items;
    private Iterator<T> innerIterator;
    private int cyclicIndex = 0;

    public CyclicIterator(List<T> items) {
        this.items = items;
        this.innerIterator = items.iterator();
    }

    @Override
    public boolean hasNext() {
        return !items.isEmpty();
    }

    @Override
    public T next() {
        if (hasNext() && !innerIterator.hasNext()) {
            innerIterator = items.iterator();
        }
        cyclicIndex = (cyclicIndex + 1) % items.size();
        return innerIterator.next();
    }

    @Override
    public void remove() {
        innerIterator.remove();
    }

    public int getCyclicIndex() {
        return cyclicIndex;
    }
}
