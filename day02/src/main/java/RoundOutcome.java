public enum RoundOutcome {
    WIN(6),
    DRAW(3),
    LOSS(0);

    private final int score;

    RoundOutcome(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
