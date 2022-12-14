import java.util.Arrays;
import java.util.List;

public record PathScan(List<Position> points) {

    public static PathScan parse(String spec) {
        List<Position> points = Arrays.stream(spec.split("->"))
                .map(String::trim)
                .map(Position::parse)
                .toList();
        return new PathScan(points);
    }
}
