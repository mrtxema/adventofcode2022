import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Blueprint(int id, Map<Commodity, Robot> robots) {
    private static final Pattern PATTERN = Pattern.compile(
            "Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore. Each clay robot costs ([0-9]+) ore. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian.");

    public static Blueprint parse(String spec) {
        Matcher matcher = PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid blueprint: " + spec);
        }
        Map<Commodity, Robot> robots = Stream.of(
                        new Robot(Commodity.ORE, Map.of(Commodity.ORE, Integer.parseInt(matcher.group(2)))),
                        new Robot(Commodity.CLAY, Map.of(Commodity.ORE, Integer.parseInt(matcher.group(3)))),
                        new Robot(Commodity.OBSIDIAN, Map.of(
                                Commodity.ORE, Integer.parseInt(matcher.group(4)),
                                Commodity.CLAY, Integer.parseInt(matcher.group(5)))),
                        new Robot(Commodity.GEODE, Map.of(
                                Commodity.ORE, Integer.parseInt(matcher.group(6)),
                                Commodity.OBSIDIAN, Integer.parseInt(matcher.group(7)))))
                .collect(Collectors.toMap(Robot::producedCommodity, Function.identity()));
        return new Blueprint(Integer.parseInt(matcher.group(1)), robots);
    }
}
