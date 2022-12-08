import java.util.List;

public class TreeGridParser {

    public TreeGrid parse(List<String> lines) {
        int[][] grid = lines.stream().map(line -> line.chars().map(c -> c - '0').toArray()).toArray(int[][]::new);
        return new TreeGrid(grid);
    }
}
