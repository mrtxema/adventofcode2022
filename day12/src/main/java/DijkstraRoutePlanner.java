import java.util.Arrays;
import java.util.List;

public class DijkstraRoutePlanner {
    private final Heightmap heightmap;
    private final GridDistanceCalculationQueue queue;

    public DijkstraRoutePlanner(Heightmap heightmap) {
        this.heightmap = heightmap;
        this.queue = new GridDistanceCalculationQueue();
    }

    public void calculateDistances() {
        queue.add(new PositionDistance(heightmap.getTargetPosition(), 0));
        while (queue.isNotEmpty()) {
            PositionDistance current = queue.pop();

            List<Position> candidates = Arrays.stream(Direction.values())
                    .map(direction -> current.position().move(direction))
                    .filter(this::isValidPosition)
                    .filter(previousPosition -> canMove(previousPosition, current.position()))
                    .toList();

            for (Position candidate : candidates) {
                int newDistance = current.distance() + 1;
                if (newDistance < queue.getDistance(candidate)) {
                    queue.add(new PositionDistance(candidate, newDistance));
                }
            }
        }
    }

    public int findShortestRouteLengthFromStart() {
        return queue.getDistance(heightmap.getStartPosition());
    }

    public int findShortestRouteLengthFromAny() {
        return heightmap.getAllPositionsWithElevation(0).stream()
                .mapToInt(queue::getDistance)
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() < heightmap.width() && position.y() >= 0 && position.y() < heightmap.height();
    }

    private boolean canMove(Position from, Position to) {
        return heightmap.getElevation(to) <= heightmap.getElevation(from) + 1;
    }
}
