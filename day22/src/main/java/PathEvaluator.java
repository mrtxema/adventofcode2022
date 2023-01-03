public class PathEvaluator {
    private final PathInfo pathInfo;

    public PathEvaluator(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public int getPasswordInMap() {
        return getPassword(new MapWrapper());
    }

    public int getPasswordInCube(boolean test) {
        return getPassword(test ? new TestCubeWrapper() : new CubeWrapper());
    }

    private int getPassword(Wrapper wrapper) {
        PathState pathState = new PathState(pathInfo.board().getInitialPosition(), Direction.RIGHT);
        for (PathInstruction pathInstruction : pathInfo.pathInstructions()) {
            pathState = apply(pathState, pathInstruction, wrapper);
        }
        return calculatePassword(pathState);
    }

    private PathState apply(PathState pathState, PathInstruction instruction, Wrapper wrapper) {
        if (instruction instanceof Move move) {
            return apply(pathState, move, wrapper);
        }
        if (instruction instanceof Turn turn) {
            return apply(pathState, turn);
        }
        throw new IllegalArgumentException("Unknown instruction: " + instruction);
    }

    private PathState apply(PathState initialState, Move move, Wrapper wrapper) {
        PathState pathState = initialState;
        for (int i = 0; i < move.tiles(); i++) {
            Position newPosition = pathState.position().move(pathState.direction());
            PathState newState = pathInfo.board().isOutOfBounds(newPosition)
                    ? wrapper.wrap(pathState, newPosition)
                    : new PathState(newPosition, pathState.direction());
            if (pathInfo.board().isWall(newState.position())) {
                break;
            }
            pathState = newState;
        }
        return pathState;
    }

    private PathState apply(PathState pathState, Turn turn) {
        return new PathState(pathState.position(), pathState.direction().rotate(turn.clockwise()));
    }

    private int calculatePassword(PathState pathState) {
        return 1000 * pathState.position().y() + 4 * pathState.position().x() + pathState.direction().getValue();
    }

    private class MapWrapper implements Wrapper {

        @Override
        public PathState wrap(PathState previousState, Position newPosition) {
            Position wrappedPostion = pathInfo.board().wrap(newPosition, previousState.direction());
            return new PathState(wrappedPostion, previousState.direction());
        }
    }
}
