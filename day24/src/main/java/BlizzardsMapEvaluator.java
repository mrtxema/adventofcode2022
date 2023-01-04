import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlizzardsMapEvaluator {
    private final BlizzardsMap blizzardsMap;

    public BlizzardsMapEvaluator(BlizzardsMap blizzardsMap) {
        this.blizzardsMap = blizzardsMap;
    }

    public int evaluateShortestPath() {
        return evaluateShortestPath(blizzardsMap.getStartPosition(), blizzardsMap.getEndPosition(), blizzardsMap.getBlizzardPositions())
                .rounds();
    }

    public int evaluateThreeLegShortestPath() {
        SolutionState leg1 = evaluateShortestPath(blizzardsMap.getStartPosition(), blizzardsMap.getEndPosition(),
                blizzardsMap.getBlizzardPositions());
        SolutionState leg2 = evaluateShortestPath(blizzardsMap.getEndPosition(), blizzardsMap.getStartPosition(), leg1.blizzards());
        SolutionState leg3 = evaluateShortestPath(blizzardsMap.getStartPosition(), blizzardsMap.getEndPosition(), leg2.blizzards());
        return leg1.rounds() + leg2.rounds() + leg3.rounds();
    }

    private SolutionState evaluateShortestPath(Position startPosition, Position targetPosition, List<Blizzard> initialBlizzards) {
        int round = 0;
        Set<Position> candidates = Set.of(startPosition);
        List<Blizzard> blizzards = initialBlizzards;
        while (!candidates.contains(targetPosition)) {
            round++;
            blizzards = blizzards.stream().map(blizzard -> blizzard.move(blizzardsMap.getWidth(), blizzardsMap.getHeight())).toList();
            Set<Position> blizzardPositions = blizzards.stream().map(Blizzard::position).collect(Collectors.toSet());
            candidates = candidates.stream().flatMap(candidate -> getCandidatePositions(candidate, blizzardPositions))
                    .collect(Collectors.toSet());
        }
        return new SolutionState(round, blizzards);
    }

    private Stream<Position> getCandidatePositions(Position currentPosition, Set<Position> blizzardPositions) {
        return Stream.concat(Stream.of(currentPosition), Arrays.stream(Direction.values()).map(currentPosition::move))
                .filter(this::isValidPosition)
                .filter(position -> !blizzardPositions.contains(position));
    }

    private boolean isValidPosition(Position position) {
        return position.equals(blizzardsMap.getStartPosition()) || position.equals(blizzardsMap.getEndPosition()) ||
                (position.x() > 0 && position.x() < blizzardsMap.getWidth() - 1
                        && position.y() > 0 && position.y() < blizzardsMap.getHeight() - 1);
    }

    private record SolutionState(int rounds, List<Blizzard> blizzards) {
    }
}
