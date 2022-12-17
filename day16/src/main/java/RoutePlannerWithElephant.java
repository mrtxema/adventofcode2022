import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RoutePlannerWithElephant {
    private static final String INITIAL_VALVE = "AA";
    private static final int MAX_TIME = 26;
    private final Map<String, ValveScan> valveScans;
    private final Map<String, GridDistanceCalculationQueue> allDistances;

    public RoutePlannerWithElephant(List<ValveScan> valveScans) {
        this.valveScans = valveScans.stream().collect(Collectors.toMap(ValveScan::id, Function.identity()));
        this.allDistances = valveScans.stream()
                .map(ValveScan::id)
                .map(this::calculateDistances)
                .collect(Collectors.toMap(GridDistanceCalculationQueue::getSourceValve, Function.identity()));
    }

    public long findMaxPressureRelease() {
        Set<String> usefulValves = valveScans.values().stream()
                .filter(valve -> valve.flowRate() > 0)
                .map(ValveScan::id)
                .collect(Collectors.toSet());
        return findMaximumFlow(usefulValves, INITIAL_VALVE, 0, 0, new HashSet<>(), false);
    }

    private long findMaximumFlow(Set<String> usefulValves, String current, long elapsedTime, long releasedPressure,
                                 Set<String> openedValves, boolean elephant) {
        long currentFlow = openedValves.stream().map(valveScans::get).mapToInt(ValveScan::flowRate).sum();
        long maxPressure = releasedPressure + currentFlow * (MAX_TIME - elapsedTime);
        if (!elephant) {
            Set<String> newCandidates = usefulValves.stream().filter(valve -> !openedValves.contains(valve)).collect(Collectors.toSet());
            long maxElephant = findMaximumFlow(newCandidates, INITIAL_VALVE, 0, 0, new HashSet<>(), true);
            maxPressure += maxElephant;
        }
        for (String next : usefulValves) {
            long timeDelta = allDistances.get(current).getDistance(next) + 1;
            if (elapsedTime + timeDelta < MAX_TIME && openedValves.add(next)) {
                long newReleasedPressure = releasedPressure + timeDelta * currentFlow;
                long pressure = findMaximumFlow(usefulValves, next, elapsedTime + timeDelta, newReleasedPressure, openedValves, elephant);
                maxPressure = Math.max(maxPressure, pressure);
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
