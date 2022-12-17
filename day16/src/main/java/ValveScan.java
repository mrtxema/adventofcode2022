import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record ValveScan(String id, int flowRate, Set<String> connectedValveIds) {
    private static final Pattern SENSOR_RESPONSE_PATTERN =
            Pattern.compile("Valve ([A-Z]+) has flow rate=([0-9]+); tunnels? leads? to valves? ([A-Z]+(?:, [A-Z]+)*)");

    public static ValveScan parse(String spec) {
        Matcher matcher = SENSOR_RESPONSE_PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid valve scan: " + spec);
        }
        return new ValveScan(matcher.group(1), Integer.parseInt(matcher.group(2)), parseConnectedValveIds(matcher.group(3)));
    }

    private static Set<String> parseConnectedValveIds(String spec) {
        return Arrays.stream(spec.split(",")).map(String::trim).collect(Collectors.toSet());
    }
}
