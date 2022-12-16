import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record SensorResponse(Position sensorPosition, Position closestBeaconPosition) {
    private static final Pattern SENSOR_RESPONSE_PATTERN =
            Pattern.compile("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)");

    public static SensorResponse parse(String spec) {
        Matcher matcher = SENSOR_RESPONSE_PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid sensor response: " + spec);
        }
        return new SensorResponse(parsePosition(matcher.group(1), matcher.group(2)), parsePosition(matcher.group(3), matcher.group(4)));
    }

    private static Position parsePosition(String x, String y) {
        return new Position(Integer.parseInt(x), Integer.parseInt(y));
    }
}
