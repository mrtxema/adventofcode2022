public record Rucksack(String firstCompartment, String secondCompartment) {

    public static Rucksack parse(String line) {
        if (line.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid line length: " + line);
        }
        int middle = line.length() / 2;
        return new Rucksack(line.substring(0, middle), line.substring(middle));
    }

    public String join() {
        return firstCompartment + secondCompartment;
    }
}
