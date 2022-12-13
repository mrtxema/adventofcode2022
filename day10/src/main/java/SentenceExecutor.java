import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SentenceExecutor {
    private final List<Integer> registerValues = new ArrayList<>();
    private int currentValue = 1;

    public SentenceExecutor execute(List<Sentence> sentences) {
        registerValues.add(currentValue);
        for (Sentence sentence : sentences) {
            for (int i = 0; i < sentence.instruction().getCycles(); i++) {
                registerValues.add(currentValue);
            }
            execute(sentence);
        }
        return this;
    }

    private void execute(Sentence sentence) {
        switch (sentence.instruction()) {
            case ADDX -> currentValue += sentence.argument();
            case NOOP -> {}
        }
    }

    public int getSignalStrength(int cycle) {
        return cycle * registerValues.get(cycle);
    }

    public String drawPixels() {
        return IntStream.range(0, 6)
                .mapToObj(row -> IntStream.range(0, 40).mapToObj(column -> getPixel(row, column)).collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getPixel(int row, int column) {
        int cycle = row * 40 + column + 1;
        int spritePosition = registerValues.get(cycle);
        boolean lit = Math.abs(spritePosition - column) <= 1;
        return lit ? "#" : ".";
    }
}
