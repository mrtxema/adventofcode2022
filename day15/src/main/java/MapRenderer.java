import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapRenderer {
    private final int maxCoordinate;

    public MapRenderer(int maxCoordinate) {
        this.maxCoordinate = maxCoordinate;
    }

    public Position findFreePosition(List<SensorResponse> sensorResponses) {
        List<Position> candidates = IntStream.range(0, sensorResponses.size() - 1)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, sensorResponses.size())
                        .boxed()
                        .flatMap(j -> findMiddlePoint(sensorResponses.get(i), sensorResponses.get(j))))
                .distinct()
                .filter(this::isValidPosition)
                .filter(position -> sensorResponses.stream().allMatch(sensorResponse -> isAtCorrectDistance(position, sensorResponse)))
                .toList();
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No free position found");
        }
        if (candidates.size() > 1) {
            throw new IllegalStateException("Multiple solutions found: " + candidates.size());
        }
        return candidates.get(0);
    }

    private boolean isAtCorrectDistance(Position position, SensorResponse sensorResponse) {
        Position sensor = sensorResponse.sensorPosition();
        return sensor.distance(position) > sensor.distance(sensorResponse.closestBeaconPosition());
    }

    private Stream<Position> findMiddlePoint(SensorResponse sensor1, SensorResponse sensor2) {
        int distance1 = sensor1.sensorPosition().distance(sensor1.closestBeaconPosition());
        int distance2 = sensor2.sensorPosition().distance(sensor2.closestBeaconPosition());
        if (sensor1.sensorPosition().distance(sensor2.sensorPosition()) > distance1 + distance2 + 2) {
            return Stream.empty();
        }
        return findPointsAtDistance(sensor1.sensorPosition(), distance1 + 1)
                .filter(point -> sensor2.sensorPosition().distance(point) == distance2 + 1);
    }

    private Stream<Position> findPointsAtDistance(Position position, int distance) {
        return IntStream.rangeClosed(position.x() - distance, position.x() + distance)
                .boxed()
                .flatMap(x -> pointsAtDistance(position, distance, x));
    }

    private Stream<Position> pointsAtDistance(Position position, int distance, int x) {
        int xDistance = Math.abs(x - position.x());
        int yDistance = distance - xDistance;
        return Stream.of(new Position(x, position.y() - yDistance), new Position(x, position.y() + yDistance)).distinct();
    }

    private boolean isValidPosition(Position position) {
        return position.x() >= 0 && position.x() <= maxCoordinate && position.y() >= 0 && position.y() <= maxCoordinate;
    }
}
