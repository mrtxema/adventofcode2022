import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CircularList<T> {
    private final List<T> values;

    public CircularList(Collection<T> values) {
        this.values = new ArrayList<>(values);
    }

    public T get(int index) {
        return values.get(innerIndex(index));
    }

    public int indexOf(T item) {
        return values.indexOf(item);
    }

    public void moveWithOffset(int currentIndex, long originalOffset) {
        int offset = (int) (originalOffset % (values.size() - 1));
        if (offset < 0) {
            moveWithOffset(currentIndex, -offset, -1);
        } else if (offset > 0) {
            moveWithOffset(currentIndex, offset, 1);
        }
    }

    private void moveWithOffset(int startingIndex, int times, int direction) {
        T temp = values.get(startingIndex);
        int currentIndex = startingIndex;
        for (int i = 0; i < times; i++) {
            int targetIndex = innerIndex(currentIndex + direction);
            values.set(currentIndex, values.get(targetIndex));
            currentIndex = targetIndex;
        }
        values.set(currentIndex, temp);
    }

    private int innerIndex(int index) {
        int n = values.size();
        return ((index % n) + n) % n;
    }

    public List<T> values() {
        return Collections.unmodifiableList(values);
    }
}
