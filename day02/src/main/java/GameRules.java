public class GameRules {

    public static boolean defeats(Shape shape1, Shape shape2) {
        return shapeDefeatedBy(shape1) == shape2;
    }

    public static Shape shapeDefeatedBy(Shape shape) {
        return switch (shape) {
            case ROCK -> Shape.SCISSORS;
            case PAPER -> Shape.ROCK;
            case SCISSORS -> Shape.PAPER;
        };
    }

    public static Shape shapeDefeats(Shape shape) {
        return switch (shape) {
            case ROCK -> Shape.PAPER;
            case PAPER -> Shape.SCISSORS;
            case SCISSORS -> Shape.ROCK;
        };
    }

    public static boolean isDraw(Shape myShape, Shape opponentShape) {
        return myShape == opponentShape;
    }

    public static Shape getShapeForOutcome(Shape opponentShape, RoundOutcome outcome) {
        return switch (outcome) {
            case WIN -> shapeDefeats(opponentShape);
            case DRAW -> opponentShape;
            case LOSS -> shapeDefeatedBy(opponentShape);
        };
    }
}
