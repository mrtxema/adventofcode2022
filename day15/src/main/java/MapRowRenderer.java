import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapRowRenderer {
    private final int row;
    private final Set<Integer> forbiddenColumns;
    private final Set<Integer> occupiedColumns;

    public MapRowRenderer(int row) {
        this.row = row;
        this.forbiddenColumns = new HashSet<>();
        this.occupiedColumns = new HashSet<>();
    }

    public MapRowRenderer processResponses(List<SensorResponse> sensorResponses) {
        sensorResponses.forEach(response -> processResponse(response, row));
        return this;
    }

    public int countFreeForbiddenPositions() {
        Set<Integer> freeForbiddenPositions = new HashSet<>(forbiddenColumns);
        freeForbiddenPositions.removeAll(occupiedColumns);
        return freeForbiddenPositions.size();
    }

    private void processResponse(SensorResponse sensorResponse, int row) {
        Position testPosition = new Position(sensorResponse.sensorPosition().x(), row);
        if (processPositionIfInRange(testPosition, sensorResponse)) {
            processPositionsWithOffset(testPosition, sensorResponse, -1);
            processPositionsWithOffset(testPosition, sensorResponse, 1);
        }
        processPositionIfInRow(sensorResponse.sensorPosition(), row);
        processPositionIfInRow(sensorResponse.closestBeaconPosition(), row);
    }

    private void processPositionsWithOffset(Position initialPosition, SensorResponse sensorResponse, int horizontalOffset) {
        Position testPosition = initialPosition;
        do {
            testPosition = testPosition.moveHorizontally(horizontalOffset);
        } while (processPositionIfInRange(testPosition, sensorResponse));
    }

    private void processPositionIfInRow(Position position, int row) {
        if (position.y() == row) {
            occupiedColumns.add(position.x());
        }
    }

    private boolean processPositionIfInRange(Position testPosition, SensorResponse sensorResponse) {
        Position sensor = sensorResponse.sensorPosition();
        if (sensor.distance(testPosition) <= sensor.distance(sensorResponse.closestBeaconPosition())) {
            forbiddenColumns.add(testPosition.x());
            return true;
        }
        return false;
    }
}
