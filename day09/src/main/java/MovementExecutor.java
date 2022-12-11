import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class MovementExecutor {
    private final Set<Position> visitedTailPositions = new HashSet<>();
    private final int knotsCount;
    private State state;

    public MovementExecutor(int knotsCount) {
        this.knotsCount = knotsCount;
        state = new State(IntStream.range(0, knotsCount).mapToObj(i -> new Position(0, 0)).toList());
        visitedTailPositions.add(state.getTailPosition());
    }

    public MovementExecutor applyMovements(List<Movement> movements) {
        movements.forEach(this::applyMovement);
        return this;
    }

    public int countVisitedTailPositions() {
        return visitedTailPositions.size();
    }

    private void applyMovement(Movement movement) {
        IntStream.range(0, movement.steps()).forEach(i -> applySingleStepMovement(movement.direction()));
    }

    private void applySingleStepMovement(Direction direction) {
        state = state.moveHead(direction);
        IntStream.range(1, knotsCount).forEach(this::moveKnotIfNeeded);
        visitedTailPositions.add(state.getTailPosition());
    }

    private void moveKnotIfNeeded(int knotIndex) {
        if (!state.isKnotTouchingPrevious(knotIndex)) {
            state = state.moveKnotToPrevious(knotIndex);
        }
        if (!state.isKnotTouchingPrevious(knotIndex)) {
            throw new IllegalStateException("Knot %d doesn't touch previous".formatted(knotIndex));
        }
    }
}
