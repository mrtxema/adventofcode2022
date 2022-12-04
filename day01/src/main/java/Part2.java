import java.util.Comparator;
import java.util.List;

public class Part2 {

    public void run(List<ElfInventory> inventories) {
        int top3CaloriesSum = inventories.stream()
                .sorted(Comparator.comparing(ElfInventory::getTotalCalories).reversed())
                .limit(3)
                .mapToInt(ElfInventory::getTotalCalories)
                .sum();
        System.out.println("The calories carried by the top3 elves are: " + top3CaloriesSum);
    }
}
