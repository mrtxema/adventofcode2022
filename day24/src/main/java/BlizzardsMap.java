import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlizzardsMap {
    private final int width;
    private final int height;
    private final Position startPosition;
    private final Position endPosition;
    private final List<Blizzard> blizzardPositions;

    public BlizzardsMap(int width, int height, Position startPosition, Position endPosition, List<Blizzard> blizzardPositions) {
        this.width = width;
        this.height = height;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.blizzardPositions = Collections.unmodifiableList(new ArrayList<>(blizzardPositions));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public List<Blizzard> getBlizzardPositions() {
        return blizzardPositions;
    }
}
