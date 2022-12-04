public class RoundParser {
    private static final char SEPARATOR = ' ';

    public Round parseAsShapePair(String line) {
        int index = line.indexOf(SEPARATOR);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid round line: " + line);
        }
        Shape opponentShape = parseOpponentShape(line.substring(0, index).trim());
        Shape myShape = parseMyShape(line.substring(index + 1).trim());
        return new Round(opponentShape, myShape);
    }

    public Round parseAsShapeAndOutcome(String line) {
        int index = line.indexOf(SEPARATOR);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid round line: " + line);
        }
        Shape opponentShape = parseOpponentShape(line.substring(0, index).trim());
        RoundOutcome outcome = parseOutcome(line.substring(index + 1).trim());
        Shape myShape = GameRules.getShapeForOutcome(opponentShape, outcome);
        return new Round(opponentShape, myShape);
    }

    private Shape parseOpponentShape(String spec) {
        return switch (spec) {
            case "A" -> Shape.ROCK;
            case "B" -> Shape.PAPER;
            case "C" -> Shape.SCISSORS;
            default -> throw new IllegalArgumentException("Invalid opponent shape: " + spec);
        };
    }

    private Shape parseMyShape(String spec) {
        return switch (spec) {
            case "X" -> Shape.ROCK;
            case "Y" -> Shape.PAPER;
            case "Z" -> Shape.SCISSORS;
            default -> throw new IllegalArgumentException("Invalid player shape: " + spec);
        };
    }

    private RoundOutcome parseOutcome(String spec) {
        return switch (spec) {
            case "X" -> RoundOutcome.LOSS;
            case "Y" -> RoundOutcome.DRAW;
            case "Z" -> RoundOutcome.WIN;
            default -> throw new IllegalArgumentException("Invalid round outcome: " + spec);
        };
    }
}
