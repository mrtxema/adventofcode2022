import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GridDistanceCalculationQueue {
    private final Map<Position, Integer> distances;
    private final TreeSet<PositionDistance> queue;

    public GridDistanceCalculationQueue() {
        this.distances = new HashMap<>();
        this.queue = new TreeSet<>(comparator());
    }

    private static Comparator<PositionDistance> comparator() {
        return Comparator.comparing(PositionDistance::distance)
                .thenComparing(pd -> pd.position().x())
                .thenComparing(pd -> pd.position().y());
    }

    public void add(PositionDistance positionDistance) {
        distances.put(positionDistance.position(), positionDistance.distance());
        queue.add(positionDistance);
    }

    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    public PositionDistance pop() {
        return queue.pollFirst();
    }

    public int getDistance(Position position) {
        return distances.getOrDefault(position, Integer.MAX_VALUE);
    }
}
