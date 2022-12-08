import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TreeGrid {
    private final int[][] grid;
    private final int rows;
    private final int columns;

    public TreeGrid(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.columns = this.rows == 0 ? 0 : grid[0].length;
        if (Arrays.stream(grid).anyMatch(row -> row.length != columns)) {
            throw new IllegalArgumentException("Rows length mismatch");
        }
    }

    public long countVisibleTrees() {
        return IntStream.range(0, rows)
                .boxed()
                .flatMap(row -> IntStream.range(0, columns).mapToObj(column -> new TreePosition(row, column)))
                .filter(this::isTreeVisible)
                .count();
    }

    public long getHighestScenicScore() {
        return IntStream.range(0, rows)
                .boxed()
                .flatMap(row -> IntStream.range(0, columns).mapToObj(column -> new TreePosition(row, column)))
                .mapToLong(this::getScenicScore)
                .max()
                .orElse(0);
    }

    private boolean isTreeVisible(TreePosition treePosition) {
        return isInEdge(treePosition) || isVisibleFromAnyEdge(treePosition);
    }

    private boolean isInEdge(TreePosition treePosition) {
        return treePosition.row == 0 || treePosition.row == rows - 1 || treePosition.column == 0 || treePosition.column == columns - 1;
    }

    private boolean isVisibleFromAnyEdge(TreePosition treePosition) {
        int treeHeight = grid[treePosition.row][treePosition.column];
        return areAllTreesShorter(getRowSection(treePosition.row, 0, treePosition.column, true), treeHeight) ||
                areAllTreesShorter(getRowSection(treePosition.row, treePosition.column + 1, columns, false), treeHeight) ||
                areAllTreesShorter(getColumnSection(treePosition.column, 0, treePosition.row, true), treeHeight) ||
                areAllTreesShorter(getColumnSection(treePosition.column, treePosition.row + 1, rows, false), treeHeight);
    }

    private boolean areAllTreesShorter(List<Integer> toEdgeTrees, int treeHeight) {
        return toEdgeTrees.stream().allMatch(toEdgeTreeHeight -> toEdgeTreeHeight < treeHeight);
    }

    private long getScenicScore(TreePosition treePosition) {
        int treeHeight = grid[treePosition.row][treePosition.column];
        return countShorterTrees(getRowSection(treePosition.row, 0, treePosition.column, true), treeHeight) *
                countShorterTrees(getRowSection(treePosition.row, treePosition.column + 1, columns, false), treeHeight) *
                countShorterTrees(getColumnSection(treePosition.column, 0, treePosition.row, true), treeHeight) *
                countShorterTrees(getColumnSection(treePosition.column, treePosition.row + 1, rows, false), treeHeight);
    }

    private long countShorterTrees(List<Integer> toEdgeTrees, int treeHeight) {
        return IntStream.range(0, toEdgeTrees.size())
                .filter(index -> toEdgeTrees.get(index) >= treeHeight)
                .boxed()
                .findFirst()
                .map(index -> index + 1)
                .orElse(toEdgeTrees.size());
    }

    private List<Integer> getRowSection(int row, int columnStart, int columnEnd, boolean reversed) {
        List<Integer> result = Arrays.stream(grid[row]).skip(columnStart).limit(columnEnd - columnStart).boxed().toList();
        return reversed ? reverse(result) : result;
    }

    private List<Integer> getColumnSection(int column, int rowStart, int rowEnd, boolean reversed) {
        List<Integer> result = Arrays.stream(grid).skip(rowStart).limit(rowEnd - rowStart).map(row -> row[column]).toList();
        return reversed ? reverse(result) : result;
    }

    private List<Integer> reverse(List<Integer> list) {
        List<Integer> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }

    private record TreePosition(int row, int column) {
    }
}
