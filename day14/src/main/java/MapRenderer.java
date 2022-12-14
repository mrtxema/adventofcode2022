import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRenderer {
    private static final List<Movement> SAND_MOVEMENTS = List.of(new Movement(0, 1), new Movement(-1, 1), new Movement(1, 1));
    private final Map<Position, Material> map;
    private final Position sandPouringPoint;
    private final int maxY;
    private int sandUnitsCounter;
    private boolean floor;

    public MapRenderer(List<PathScan> pathScans, Position sandPouringPoint) {
        this.map = new HashMap<>();
        this.sandPouringPoint = sandPouringPoint;
        this.maxY = pathScans.stream().flatMap(pathScan -> pathScan.points().stream()).mapToInt(Position::y).max().orElse(0);
        this.sandUnitsCounter = 0;
        this.floor = false;
        pathScans.forEach(this::renderPath);
    }

    private void renderPath(PathScan pathScan) {
        Position previousPoint = pathScan.points().get(0);
        for (Position point : pathScan.points().subList(1, pathScan.points().size())) {
            renderPathSection(previousPoint, point);
            previousPoint = point;
        }
    }

    private void renderPathSection(Position start, Position end) {
        Movement offset = new Movement((int) Math.signum(end.x() - start.x()), (int) Math.signum(end.y() - start.y()));
        Position currentPosition = start;
        map.put(currentPosition, Material.ROCK);
        while (!currentPosition.equals(end)) {
            currentPosition = currentPosition.move(offset);
            map.put(currentPosition, Material.ROCK);
        }
    }

    public MapRenderer addFloor() {
        floor = true;
        return this;
    }

    public MapRenderer simulateSandFall() {
        while (simulateSandUnitFall()) {
            sandUnitsCounter++;
        }
        return this;
    }

    private boolean simulateSandUnitFall() {
        Position sandPosition = sandPouringPoint;
        if (!isPositionFree(sandPosition)) {
            return false;
        }
        Position previousPosition;
        do {
            previousPosition = sandPosition;
            sandPosition = simulateSandUnitFallStep(previousPosition);
        } while (!sandPosition.equals(previousPosition) && !flewDown(sandPosition));
        if (flewDown(sandPosition)) {
            return false;
        }
        map.put(sandPosition, Material.SAND);
        return true;
    }

    private boolean flewDown(Position sandPosition) {
        return !floor && sandPosition.y() > maxY;
    }

    private Position simulateSandUnitFallStep(Position previousPosition) {
        return SAND_MOVEMENTS.stream()
                .map(previousPosition::move)
                .filter(this::isPositionFree)
                .findFirst()
                .orElse(previousPosition);
    }

    private boolean isPositionFree(Position position) {
        return !isFloor(position) && map.getOrDefault(position, Material.AIR) == Material.AIR;
    }

    private boolean isFloor(Position position) {
        return floor && position.y() == maxY + 2;
    }

    public int countSandUnits() {
        return sandUnitsCounter;
    }
}
