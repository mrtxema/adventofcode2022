public class Inventory {
    private int numOreRobots;
    private int numClayRobots;
    private int numObsidianRobots;
    private int numGeodeRobots;
    private Robot notBuiltRobot;
    private int numOre;
    private int numClay;
    private int numObsidian;
    private int numGeode;

    public Inventory(int numOreRobots) {
        this(numOreRobots, 0, 0, 0, null, 0, 0, 0, 0);
    }

    public Inventory(int numOreRobots, int numClayRobots, int numObsidianRobots, int numGeodeRobots, Robot notBuiltRobot, int numOre, int numClay, int numObsidian, int numGeode) {
        this.numOreRobots = numOreRobots;
        this.numClayRobots = numClayRobots;
        this.numObsidianRobots = numObsidianRobots;
        this.numGeodeRobots = numGeodeRobots;
        this.notBuiltRobot = notBuiltRobot;
        this.numOre = numOre;
        this.numClay = numClay;
        this.numObsidian = numObsidian;
        this.numGeode = numGeode;
    }

    public boolean canBuy(Robot robot) {
        return robot.price().entrySet().stream().allMatch(price -> getAmount(price.getKey()) >= price.getValue());
    }

    public Inventory buy(Robot robot) {
        if (!canBuy(robot)) {
            throw new IllegalStateException("Can't buy robot " + robot);
        }
        if (notBuiltRobot != null) {
            throw new IllegalStateException("A robot has already been bought at this time " + robot);
        }
        int newNumOre = numOre - robot.price().getOrDefault(Commodity.ORE, 0);
        int newNumClay = numClay - robot.price().getOrDefault(Commodity.CLAY, 0);
        int newNumObsidian = numObsidian - robot.price().getOrDefault(Commodity.OBSIDIAN, 0);
        int newNumGeode = numGeode - robot.price().getOrDefault(Commodity.GEODE, 0);
        return new Inventory(numOreRobots, numClayRobots, numObsidianRobots, numGeodeRobots, robot, newNumOre, newNumClay, newNumObsidian, newNumGeode);
    }

    public Inventory copy() {
        return new Inventory(numOreRobots, numClayRobots, numObsidianRobots, numGeodeRobots, notBuiltRobot, numOre, numClay, numObsidian, numGeode);
    }

    public Inventory collectCommodities() {
        numOre += numOreRobots;
        numClay += numClayRobots;
        numObsidian += numObsidianRobots;
        numGeode += numGeodeRobots;
        return this;
    }

    public Inventory consolidate() {
        if (notBuiltRobot != null) {
            switch (notBuiltRobot.producedCommodity()) {
                case ORE -> numOreRobots++;
                case CLAY -> numClayRobots++;
                case OBSIDIAN -> numObsidianRobots++;
                case GEODE -> numGeodeRobots++;
            }
            notBuiltRobot = null;
        }
        return this;
    }

    public int getAmount(Commodity commodity) {
        return switch (commodity) {
            case ORE -> numOre;
            case CLAY -> numClay;
            case OBSIDIAN -> numObsidian;
            case GEODE -> numGeode;
        };
    }

    public int countRobots(Robot robot) {
        return switch (robot.producedCommodity()) {
            case ORE -> numOreRobots;
            case CLAY -> numClayRobots;
            case OBSIDIAN -> numObsidianRobots;
            case GEODE -> numGeodeRobots;
        };
    }

    public int getMinGeodes(int remainingMinutes) {
        return numGeode + numGeodeRobots * remainingMinutes;
    }

    public int estimateMaxGeodes(int remainingMinutes) {
        int total = getMinGeodes(remainingMinutes);
        return remainingMinutes > 1 ? total + remainingMinutes * (remainingMinutes - 1) / 2 : total;
    }

    public boolean makesSenseToBuy(Robot robot, Blueprint blueprint) {
        if (robot.producedCommodity() == Commodity.GEODE) {
            return true;
        }
        int maxCommodityCost = blueprint.robots().values().stream()
                .mapToInt(r -> r.price().getOrDefault(robot.producedCommodity(), 0))
                .max()
                .orElse(0);
        return countRobots(robot) < maxCommodityCost;
    }
}
