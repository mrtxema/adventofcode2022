public class CubeWrapper implements Wrapper {

    @Override
    public PathState wrap(PathState previousState, Position newPosition) {
        if (is(previousState, 51, 100, 1, 1, Direction.UP)) {
            return new PathState(new Position(1, previousState.position().x() + 100), Direction.RIGHT);
        }
        if (is(previousState, 1, 50, 101, 101, Direction.UP)) {
            return new PathState(new Position(51, previousState.position().x() + 50), Direction.RIGHT);
        }
        if (is(previousState, 101, 150, 1, 1, Direction.UP)) {
            return new PathState(new Position(previousState.position().x() - 100, 200), Direction.UP);
        }

        if (is(previousState, 101, 150, 50, 50, Direction.DOWN)) {
            return new PathState(new Position(100, previousState.position().x() - 50), Direction.LEFT);
        }
        if (is(previousState, 51, 100, 150, 150, Direction.DOWN)) {
            return new PathState(new Position(50, previousState.position().x() + 100), Direction.LEFT);
        }
        if (is(previousState, 1, 50, 200, 200, Direction.DOWN)) {
            return new PathState(new Position(previousState.position().x() + 100, 1), Direction.DOWN);
        }

        if (is(previousState, 1, 1, 101, 150, Direction.LEFT)) {
            return new PathState(new Position(51, 151 - previousState.position().y()), Direction.RIGHT);
        }
        if (is(previousState, 51, 51, 1, 50, Direction.LEFT)) {
            return new PathState(new Position(1, 151 - previousState.position().y()), Direction.RIGHT);
        }
        if (is(previousState, 51, 51, 51, 100, Direction.LEFT)) {
            return new PathState(new Position(previousState.position().y() - 50, 101), Direction.DOWN);
        }
        if (is(previousState, 1, 1, 151, 200, Direction.LEFT)) {
            return new PathState(new Position(previousState.position().y() - 100, 1), Direction.DOWN);
        }

        if (is(previousState, 100, 100, 51, 100, Direction.RIGHT)) {
            return new PathState(new Position(previousState.position().y() + 50, 50), Direction.UP);
        }
        if (is(previousState, 150, 150, 1, 50, Direction.RIGHT)) {
            return new PathState(new Position(100, 151 - previousState.position().y()), Direction.LEFT);
        }
        if (is(previousState, 100, 100, 101, 150, Direction.RIGHT)) {
            return new PathState(new Position(150, 151 - previousState.position().y()), Direction.LEFT);
        }
        if (is(previousState, 50, 50, 151, 200, Direction.RIGHT)) {
            return new PathState(new Position(previousState.position().y() - 100, 150), Direction.UP);
        }

        throw new IllegalStateException("Wrap not applied");
    }

    private boolean is(PathState state, int minx, int maxx, int miny, int maxy, Direction direction) {
        return in(state.position().x(), minx, maxx) && in(state.position().y(), miny, maxy) && state.direction() == direction;
    }

    private boolean in(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
