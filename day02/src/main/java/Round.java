public record Round(Shape opponentShape, Shape myShape) {

    public int getScore() {
        return myShape().getScore() + getOutcome().getScore();
    }

    public RoundOutcome getOutcome() {
        if (GameRules.defeats(myShape, opponentShape)) {
            return RoundOutcome.WIN;
        }
        if (GameRules.isDraw(myShape, opponentShape)) {
            return RoundOutcome.DRAW;
        }
        if (GameRules.defeats(opponentShape, myShape)) {
            return RoundOutcome.LOSS;
        }
        throw new IllegalStateException("Unknown round outcome: " + this);
    }
}
