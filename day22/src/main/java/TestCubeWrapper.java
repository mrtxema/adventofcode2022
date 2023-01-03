public class TestCubeWrapper implements Wrapper {

    @Override
    public PathState wrap(PathState previousState, Position newPosition) {
        if (is(previousState, 9, 12, 1, 1, Direction.UP)) {
            return new PathState(new Position(13 - previousState.position().x(), 5), Direction.DOWN);
        }
        if (is(previousState, 1, 4, 5, 5, Direction.UP)) {
            return new PathState(new Position(13 - previousState.position().x(), 1), Direction.DOWN);
        }
        if (is(previousState, 5, 8, 5, 5, Direction.UP)) {
            return new PathState(new Position(9, previousState.position().x() - 4), Direction.RIGHT);
        }
        if (is(previousState, 13, 16, 9, 9, Direction.UP)) {
            return new PathState(new Position(12, 21 - previousState.position().x()), Direction.LEFT);
        }

        if (is(previousState, 1, 4, 8, 8, Direction.DOWN)) {
            return new PathState(new Position(13 - previousState.position().x(), 12), Direction.UP);
        }
        if (is(previousState, 5, 8, 8, 8, Direction.DOWN)) {
            return new PathState(new Position(9, 17 - previousState.position().x()), Direction.RIGHT);
        }
        if (is(previousState, 9, 12, 12, 12, Direction.DOWN)) {
            return new PathState(new Position(13 - previousState.position().x(), 8), Direction.UP);
        }
        if (is(previousState, 13, 16, 12, 12, Direction.DOWN)) {
            return new PathState(new Position(1, 21 - previousState.position().x()), Direction.RIGHT);
        }

        if (is(previousState, 9, 9, 1, 4, Direction.LEFT)) {
            return new PathState(new Position(4 + previousState.position().y(), 5), Direction.DOWN);
        }
        if (is(previousState, 1, 1, 5, 8, Direction.LEFT)) {
            return new PathState(new Position(21 - previousState.position().y(), 12), Direction.UP);
        }
        if (is(previousState, 9, 9, 9, 12, Direction.LEFT)) {
            return new PathState(new Position(17 - previousState.position().y(), 8), Direction.UP);
        }

        if (is(previousState, 12, 12, 1, 4, Direction.RIGHT)) {
            return new PathState(new Position(16, 13 - previousState.position().y()), Direction.LEFT);
        }
        if (is(previousState, 12, 12, 5, 8, Direction.RIGHT)) {
            return new PathState(new Position(21 - previousState.position().y(), 9), Direction.DOWN);
        }
        if (is(previousState, 16, 16, 9, 12, Direction.RIGHT)) {
            return new PathState(new Position(12, 13 - previousState.position().y()), Direction.LEFT);
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
