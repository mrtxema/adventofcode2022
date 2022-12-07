public class Part2 {
    private static final long MAX_SIZE = 40_000_000;
    private final DirectorySizeCalculator calculator;

    public Part2(DirectorySizeCalculator calculator) {
        this.calculator = calculator;
    }

    public void run(Directory rootDirectory) {
        long totalSize = calculator.getDirectorySize(rootDirectory);
        long needToFree = totalSize - MAX_SIZE;
        long selectedDirSize = calculator.getAllDirectories().stream()
                .mapToLong(calculator::getDirectorySize)
                .filter(size -> size >= needToFree)
                .min()
                .orElse(0);
        System.out.println("Part 2. The total size of the selected directory is: " + selectedDirSize);
    }
}
