public class Part1 {
    private static final long SIZE_LIMIT = 100_000;
    private final DirectorySizeCalculator calculator;

    public Part1(DirectorySizeCalculator calculator) {
        this.calculator = calculator;
    }

    public void run() {
        long totalSize = calculator.getAllDirectories().stream()
                .mapToLong(calculator::getDirectorySize)
                .filter(size -> size <= SIZE_LIMIT)
                .sum();
        System.out.println("Part 1. The total size is: " + totalSize);
    }
}
