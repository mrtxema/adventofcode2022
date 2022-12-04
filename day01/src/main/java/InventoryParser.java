import java.util.ArrayList;
import java.util.List;

public class InventoryParser {

    public List<ElfInventory> parseInventories(List<String> lines) {
        List<ElfInventory> result = new ArrayList<>();
        List<Integer> currentElfCalories = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                addInventoryIfNotEmpty(currentElfCalories, result);
                currentElfCalories.clear();
            } else {
                currentElfCalories.add(Integer.valueOf(line));
            }
        }
        addInventoryIfNotEmpty(currentElfCalories, result);
        return result;
    }

    private void addInventoryIfNotEmpty(List<Integer> currentElfCalories, List<ElfInventory> inventories) {
        if (!currentElfCalories.isEmpty()) {
            inventories.add(new ElfInventory(currentElfCalories));
        }
    }
}
