import java.util.ArrayList;
import java.util.List;

public class BlizzardsMapParser {

    public BlizzardsMap parse(List<String> lines) {
        return new BlizzardsMap(
                lines.get(0).length(),
                lines.size(),
                new Position(lines.get(0).indexOf('.'), 0),
                new Position(lines.get(lines.size() - 1).indexOf('.'), lines.size() - 1),
                parseBlizzardPositions(lines));
    }

    private List<Blizzard> parseBlizzardPositions(List<String> lines) {
        List<Blizzard> result = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c != '#' && c != '.') {
                    result.add(new Blizzard(new Position(x, y), Direction.parse(c)));
                }
            }
        }
        return result;
    }
}
