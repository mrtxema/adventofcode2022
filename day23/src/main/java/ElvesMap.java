import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElvesMap {
    private final Map<Integer, Elf> elves;
    private final Set<Position> positions;

    public ElvesMap(Collection<Elf> elves) {
        this.elves = elves.stream().collect(Collectors.toMap(Elf::id, Function.identity()));
        this.positions = elves.stream().map(Elf::position).collect(Collectors.toSet());
    }

    public Collection<Elf> getElves() {
        return Collections.unmodifiableCollection(elves.values());
    }

    public boolean isAnyElf(Position position) {
        return positions.contains(position);
    }

    public ElvesMap applyProposals(List<Elf> proposals) {
        Map<Position, List<Elf>> proposalsByPosition = proposals.stream().collect(Collectors.groupingBy(Elf::position));
        List<Elf> newPositions = new ArrayList<>();
        for (List<Elf> elvesInSamePosition : proposalsByPosition.values()) {
            if (elvesInSamePosition.size() == 1) {
                newPositions.add(elvesInSamePosition.get(0));
            } else {
                newPositions.addAll(elvesInSamePosition.stream().map(elf -> elves.get(elf.id())).toList());
            }
        }
        return new ElvesMap(newPositions);
    }

    public int countEmptyTiles() {
        var xStats = positions.stream().mapToInt(Position::x).summaryStatistics();
        var yStats = positions.stream().mapToInt(Position::y).summaryStatistics();
        int width = xStats.getMax() - xStats.getMin() + 1;
        int height = yStats.getMax() - yStats.getMin() + 1;
        int totalTiles = width * height;
        return totalTiles - elves.size();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ElvesMap other && elves.equals(other.elves);
    }
}
