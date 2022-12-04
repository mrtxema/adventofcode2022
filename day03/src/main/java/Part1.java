import java.util.List;

public class Part1 {
    private final RucksackService rucksackService;

    public Part1(RucksackService rucksackService) {
        this.rucksackService = rucksackService;
    }

    public void run(List<Rucksack> rucksacks) {
        int prioritySum = rucksacks.stream()
                .map(rucksackService::findRepeatedItemType)
                .mapToInt(rucksackService::getItemTypePriority)
                .sum();
        System.out.printf("The sum of priorities is: %d%n", prioritySum);
    }
}
