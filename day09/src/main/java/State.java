import java.util.List;
import java.util.stream.IntStream;

public class State {
    private final List<Position> knotPositions;

    public State(List<Position> knotPositions) {
        this.knotPositions = knotPositions;
    }

    public State moveHead(Direction direction) {
        List<Position> newKnotPositions = IntStream.range(0, knotPositions.size())
                .mapToObj(index -> index == 0 ? knotPositions.get(index).move(direction) : knotPositions.get(index))
                .toList();
        return new State(newKnotPositions);
    }

    public Position getTailPosition() {
        return knotPositions.get(knotPositions.size() - 1);
    }

    public boolean isKnotTouchingPrevious(int knotIndex) {
        Position previousPosition = knotPositions.get(knotIndex - 1);
        Position currentPosition = knotPositions.get(knotIndex);
        int xDiff = Math.abs(previousPosition.x() - currentPosition.x());
        int yDiff = Math.abs(previousPosition.y() - currentPosition.y());
        return xDiff <= 1 && yDiff <= 1;
    }

    public State moveKnotToPrevious(int knotIndex) {
        Position previousPosition = knotPositions.get(knotIndex - 1);
        Position currentPosition = knotPositions.get(knotIndex);
        int xDiff = (int) Math.signum(previousPosition.x() - currentPosition.x());
        int yDiff = (int) Math.signum(previousPosition.y() - currentPosition.y());
        Position newPosition = new Position(currentPosition.x() + xDiff, currentPosition.y() + yDiff);
        List<Position> newKnotPositions = IntStream.range(0, knotPositions.size())
                .mapToObj(index -> index == knotIndex ? newPosition : knotPositions.get(index))
                .toList();
        return new State(newKnotPositions);
    }
}
