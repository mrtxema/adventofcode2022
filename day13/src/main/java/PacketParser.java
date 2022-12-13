import java.util.ArrayList;
import java.util.List;

public class PacketParser {

    public Packet parse(String spec) {
        ParseResponse response = parseList(spec, 0);
        if (response.index() != spec.length()) {
            throw new IllegalArgumentException("Unexpected tail: " + spec.substring(response.index()));
        }
        if (response.item() instanceof ListItem listItem) {
            return new Packet(listItem);
        }
        throw new IllegalArgumentException("Unexpected type: " + response.item());
    }

    private ParseResponse parseList(String spec, int startingIndex) {
        if (spec.charAt(startingIndex) != '[') {
            throw new IllegalArgumentException("Expected list at index %d. Found %s".formatted(startingIndex, spec.charAt(startingIndex)));
        }
        List<Item> values = new ArrayList<>();
        int index = startingIndex + 1;
        while (spec.charAt(index) != ']') {
            ParseResponse response = parseItem(spec, index);
            values.add(response.item());
            index = response.index();
            if (spec.charAt(index) == ',') {
                index++;
            }
        }
        return new ParseResponse(new ListItem(values), index + 1);
    }

    private ParseResponse parseItem(String spec, int startingIndex) {
        if (spec.charAt(startingIndex) == '[') {
            return parseList(spec, startingIndex);
        }
        int endIndex = startingIndex;
        while (Character.isDigit(spec.charAt(endIndex))) {
            endIndex++;
        }
        return new ParseResponse(new IntegerItem(Integer.parseInt(spec.substring(startingIndex, endIndex))), endIndex);
    }

    private record ParseResponse(Item item, int index) {
    }
}
