import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item left, Item right) {
        if (left instanceof ListItem leftList) {
            if (right instanceof ListItem rightList) {
                return compareLists(leftList, rightList);
            } else if (right instanceof IntegerItem rightInt) {
                return compareLists(leftList, rightInt.asList());
            }
        } else if (left instanceof IntegerItem leftInt) {
            if (right instanceof ListItem rightList) {
                return compareLists(leftInt.asList(), rightList);
            } else if (right instanceof IntegerItem rightInt) {
                return compareIntegers(leftInt, rightInt);
            }
        }
        throw new IllegalArgumentException("Unexpected item types: %s - %s".formatted(left, right));
    }

    private int compareLists(ListItem left, ListItem right) {
        int commonLength = Math.min(left.size(), right.size());
        for (int i = 0; i < commonLength; i++) {
            int itemComparison = compare(left.getValues().get(i), right.getValues().get(i));
            if (itemComparison != 0) {
                return itemComparison;
            }
        }
        return Integer.compare(left.size(), right.size());
    }

    private int compareIntegers(IntegerItem left, IntegerItem right) {
        return Integer.compare(left.getValue(), right.getValue());
    }
}
