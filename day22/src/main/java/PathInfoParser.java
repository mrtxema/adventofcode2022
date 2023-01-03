import java.util.ArrayList;
import java.util.List;

public class PathInfoParser {

    public PathInfo parse(List<String> lines) {
        return new PathInfo(new Board(lines.subList(0, lines.size() - 1)), parseInstructions(lines.get(lines.size() - 1).trim()));
    }

    private List<PathInstruction> parseInstructions(String spec) {
        int index = 0;
        List<PathInstruction> result = new ArrayList<>();
        while (index < spec.length()) {
            int nextIndex = findNextDirectionIndex(spec, index);
            result.add(PathInstruction.parse(spec.substring(index, nextIndex)));
            if (nextIndex < spec.length()) {
                result.add(PathInstruction.parse(spec.substring(nextIndex, nextIndex + 1)));
            }
            index = nextIndex + 1;
        }
        return result;
    }

    private int findNextDirectionIndex(String spec, int startingIndex) {
        int index = startingIndex;
        while (index < spec.length() && Character.isDigit(spec.charAt(index))) {
            index++;
        }
        return index;
    }
}
