import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListItem implements Item {
    private final List<Item> values;

    public ListItem(List<Item> values) {
        this.values = Collections.unmodifiableList(new ArrayList<>(values));
    }

    public List<Item> getValues() {
        return values;
    }

    public int size() {
        return values.size();
    }
}
