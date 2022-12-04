import java.util.ArrayList;
import java.util.List;

public class ElfInventory {
    private final List<Integer> calories;

    public ElfInventory(List<Integer> calories) {
        this.calories = new ArrayList<>(calories);
    }

    public int getTotalCalories() {
        return calories.stream().mapToInt(i -> i).sum();
    }
}
