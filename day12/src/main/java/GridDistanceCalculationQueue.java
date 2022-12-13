import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class GridDistanceCalculationQueue {
    private final int width;
    private final int[] distances;
    private final TreeSet<PositionDistance> queue;

    public GridDistanceCalculationQueue(int width, int height) {
        this.width = width;
        this.distances = IntStream.range(0, height * width).map(i -> Integer.MAX_VALUE).toArray();
        this.queue = new TreeSet<>(Comparator.comparing(PositionDistance::distance).thenComparing(pd -> index(pd.position())));
    }

    public void add(PositionDistance positionDistance) {
        distances[index(positionDistance.position())] = positionDistance.distance();
        queue.add(positionDistance);
    }

    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    public PositionDistance pop() {
        PositionDistance current = queue.first();
        queue.remove(current);
        return current;
    }

    public int getDistance(Position position) {
        return distances[index(position)];
    }

    private int index(Position position) {
        return position.y() * width + position.x();
    }
}
