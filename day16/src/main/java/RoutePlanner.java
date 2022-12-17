import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

public class RoutePlanner {
    private static final String INITIAL_VALVE = "AA";
    private static final int MAX_TIME = 30;
    private final Map<String, ValveScan> valveScans;

    public RoutePlanner(List<ValveScan> valveScans) {
        this.valveScans = valveScans.stream().collect(Collectors.toMap(ValveScan::id, Function.identity()));
    }

    public long findMaxPressureRelease() {
        return findMaximumFlow(INITIAL_VALVE, 0, 0, new HashSet<>());
    }

    private long findMaximumFlow(String current, long elapsedTime, long releasedPressure, Set<String> openedValves) {
        long currentFlow = openedValves.stream().map(valveScans::get).mapToInt(ValveScan::flowRate).sum();
        long maxPressure = releasedPressure + currentFlow * (MAX_TIME - elapsedTime);
        GridDistanceCalculationQueue distances = calculateDistances(current);
        List<String> usefulValves = valveScans.values().stream()
                .filter(valve -> valve.flowRate() > 0)
                .map(ValveScan::id)
                .filter(id -> !openedValves.contains(id))
                .toList();
        for (String next : usefulValves) {
            long timeDelta = distances.getDistance(next) + 1;
            if (elapsedTime + timeDelta < MAX_TIME) {
                long newReleasedPressure = releasedPressure + timeDelta * currentFlow;
                openedValves.add(next);
                maxPressure = Math.max(maxPressure, findMaximumFlow(next, elapsedTime + timeDelta, newReleasedPressure, openedValves));
                openedValves.remove(next);
            }
        }
        return maxPressure;
    }

    private GridDistanceCalculationQueue calculateDistances(String sourceValveId) {
        GridDistanceCalculationQueue queue = new GridDistanceCalculationQueue(sourceValveId);
        queue.add(new ValveDistance(sourceValveId, 0));
        while (queue.isNotEmpty()) {
            ValveDistance current = queue.pop();
            for (String connectedValve : valveScans.get(current.valveId()).connectedValveIds()) {
                int newDistance = current.distance() + 1;
                if (newDistance < queue.getDistance(connectedValve)) {
                    queue.add(new ValveDistance(connectedValve, newDistance));
                }
            }
        }
        return queue;
    }
}
