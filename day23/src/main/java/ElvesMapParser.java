import java.util.ArrayList;
import java.util.List;

public class ElvesMapParser {

    public ElvesMap parse(List<String> lines) {
        List<Elf> elves = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    elves.add(new Elf(elves.size(), new Position(x, y)));
                }
            }
        }
        return new ElvesMap(elves);
    }
}
