import java.util.List;
import java.util.stream.IntStream;

public class Part2 {
    private final RucksackService rucksackService;

    public Part2(RucksackService rucksackService) {
        this.rucksackService = rucksackService;
    }

    public void run(List<Rucksack> rucksacks) {
        if (rucksacks.size() % 3 != 0) {
            throw new IllegalArgumentException("The rucksack list can't be grouped in groups of three. Size " + rucksacks.size());
        }
        int prioritySum = IntStream.range(0, rucksacks.size() / 3)
                .map(i -> i * 3)
                .mapToObj(i -> rucksacks.subList(i, i + 3))
                .map(rucksackService::findRepeatedItemType)
                .mapToInt(rucksackService::getItemTypePriority)
                .sum();
        System.out.printf("The sum of priorities is: %d%n", prioritySum);
    }
}
