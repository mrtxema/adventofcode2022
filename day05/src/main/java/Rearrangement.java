import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Rearrangement(int numCrates, String sourceStackId, String targetStackId) {
    private static final Pattern REARRANGEMENT_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    public static Rearrangement parse(String spec) {
        Matcher matcher = REARRANGEMENT_PATTERN.matcher(spec);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid arrangement line: " + spec);
        }
        return new Rearrangement(Integer.parseInt(matcher.group(1)), matcher.group(2), matcher.group(3));
    }
}
