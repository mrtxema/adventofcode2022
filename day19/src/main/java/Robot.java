import java.util.Map;

public record Robot(Commodity producedCommodity, Map<Commodity, Integer> price) {
}
