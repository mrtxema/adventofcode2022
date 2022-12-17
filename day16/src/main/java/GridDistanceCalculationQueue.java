import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GridDistanceCalculationQueue {
    private final Map<String, Integer> distances;
    private final TreeSet<ValveDistance> queue;
    private final String sourceValve;

    public GridDistanceCalculationQueue(String sourceValve) {
        this.distances = new HashMap<>();
        this.queue = new TreeSet<>(comparator());
        this.sourceValve = sourceValve;
    }

    private static Comparator<ValveDistance> comparator() {
        return Comparator.comparing(ValveDistance::distance).thenComparing(ValveDistance::valveId);
    }

    public void add(ValveDistance valveDistance) {
        distances.put(valveDistance.valveId(), valveDistance.distance());
        queue.add(valveDistance);
    }

    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    public ValveDistance pop() {
        return queue.pollFirst();
    }

    public int getDistance(String valveId) {
        return distances.getOrDefault(valveId, Integer.MAX_VALUE);
    }

    public String getSourceValve() {
        return sourceValve;
    }
}
