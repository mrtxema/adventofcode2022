import java.util.List;

public class IntegerItem implements Item {
    private final int value;

    public IntegerItem(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public ListItem asList() {
        return new ListItem(List.of(this));
    }
}
