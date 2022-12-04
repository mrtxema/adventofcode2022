import java.util.List;

public class Part1 {

    public void run(List<ElfInventory> inventories) {
        int maxCalories = inventories.stream().mapToInt(ElfInventory::getTotalCalories).max().orElse(0);
        System.out.println("The max calories carried are: " + maxCalories);
    }
}
