import java.util.List;
import java.util.stream.Stream;

public class BlueprintOptimizer {
    private final Blueprint blueprint;

    public BlueprintOptimizer(Blueprint blueprint) {
        this.blueprint = blueprint;
    }

    public int calculateQualityLevel(int initialMinutes) {
        return maxGeodeProduction(initialMinutes) * blueprint.id();
    }

    public int maxGeodeProduction(int initialMinutes) {
        List<Inventory> inventories = List.of(new Inventory(1));
        int remainingMinutes = initialMinutes;
        while (remainingMinutes > 1) {
            final int minutes = remainingMinutes;
            int minValue = inventories.stream().mapToInt(inventory -> inventory.getMinGeodes(minutes)).max().orElse(0);
            inventories = inventories.stream()
                    .flatMap(this::buyRobots)
                    .map(Inventory::collectCommodities)
                    .map(Inventory::consolidate)
                    .filter(inventory -> inventory.estimateMaxGeodes(minutes - 1) >= minValue)
                    .toList();
            remainingMinutes--;
        }
        int result = inventories.stream()
                .map(Inventory::collectCommodities)
                .mapToInt(inventory -> inventory.getAmount(Commodity.GEODE))
                .max()
                .orElse(0);
        System.out.printf("Finished processing blueprint %d. Max geode production is %d%n", blueprint.id(),  result);
        return result;
    }

    private Stream<Inventory> buyRobots(Inventory inventory) {
        Robot geodeRobot = blueprint.robots().get(Commodity.GEODE);
        if (inventory.canBuy(geodeRobot) && inventory.makesSenseToBuy(geodeRobot, blueprint)) {
            return Stream.of(inventory.buy(geodeRobot));
        }
        Robot obsidianRobot = blueprint.robots().get(Commodity.OBSIDIAN);
        if (inventory.canBuy(obsidianRobot) && inventory.makesSenseToBuy(obsidianRobot, blueprint)) {
            return Stream.of(inventory.buy(obsidianRobot));
        }
        Stream<Inventory> buys = Stream.of(Commodity.CLAY, Commodity.ORE)
                .map(blueprint.robots()::get)
                .filter(inventory::canBuy)
                .filter(robot -> inventory.makesSenseToBuy(robot, blueprint))
                .map(inventory::buy);
        return Stream.concat(Stream.of(inventory.copy()), buys);
    }
}
