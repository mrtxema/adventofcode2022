import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class RockFallSimulator {
    private final Chamber chamber;
    private final CyclicIterator<Rock> rocks;
    private final CyclicIterator<Integer> gasJets;
    private long rocksSimulated;

    public RockFallSimulator(List<Integer> gasJets) {
        this.chamber = new Chamber();
        this.rocks = new CyclicIterator<>(Rock.getAllRocks());
        this.gasJets = new CyclicIterator<>(gasJets);
        this.rocksSimulated = 0;
    }

    public RockFallSimulator simulateRockFalls(int numRocks) {
        IntStream.range(0, numRocks).forEach(i -> simulateRockFall(rocks.next()));
        return this;
    }

    public long simulateRockFallsOptimized(long numRocks) {
        CycleInformation cycleInformation = detectCycle();
        long remainingRocks = numRocks - rocksSimulated;
        if (remainingRocks < 0) {
            throw new IllegalStateException("Cycle is smaller than the requested rocks. Use the simulateRockFalls method instead");
        }
        int offsetRocks = (int) (remainingRocks % cycleInformation.size());
        simulateRockFalls(offsetRocks);
        long numCycles = remainingRocks / cycleInformation.size();
        return chamber.getHeight() + cycleInformation.height() * numCycles;
    }

    private void simulateRockFall(Rock rock) {
        Position position = new Position(2, chamber.getHeight() + 3);
        Position previousPosition;
        do {
            previousPosition = position;
            position = position.move(gasJets.next(), 0);
            if (chamber.collides(rock.place(position))) {
                position = previousPosition;
            } else {
                previousPosition = position;
            }
            position = position.move(0, -1);
        } while (!chamber.collides(rock.place(position)));
        chamber.putItem(rock.place(previousPosition));
        rocksSimulated++;
    }

    public int getMaxHeight() {
        return chamber.getHeight();
    }

    public RockFallSimulator drawChamber() {
        chamber.draw().forEach(System.out::println);
        return this;
    }

    private CycleInformation detectCycle() {
        int rockCatalogSize = Rock.getAllRocks().size();
        Map<CycleState, SimulationState> skylines = new HashMap<>();
        CycleState cycleState;
        boolean detected;
        do {
            simulateRockFalls(rockCatalogSize);
            cycleState = new CycleState(gasJets.getCyclicIndex(), chamber.getSkyline());
            detected = skylines.containsKey(cycleState);
            if (!detected) {
                skylines.put(cycleState, new SimulationState(rocksSimulated, chamber.getHeight()));
            }
        } while (!detected);
        SimulationState cycleStartState = skylines.get(cycleState);
        return new CycleInformation(rocksSimulated - cycleStartState.numRocks(), chamber.getHeight() - cycleStartState.height());
    }

    private record CycleState(int gasJetIndex, List<Integer> skyline) {
    }

    private record SimulationState(long numRocks, int height) {
    }

    private record CycleInformation(long size, long height) {
    }
}
