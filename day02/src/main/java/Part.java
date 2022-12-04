import java.util.List;

public class Part {
    private final int number;

    public Part(int number) {
        this.number = number;
    }

    public void run(List<Round> rounds) {
        int totalScore = rounds.stream().mapToInt(Round::getScore).sum();
        System.out.printf("Part %d. The total score is: %d%n", number, totalScore);
    }
}
