public record AssignmentPair(Assignment firstAssignment, Assignment secondAssignment) {
    private static final char SEPARATOR = ',';

    public static AssignmentPair parse(String line) {
        int index = line.indexOf(SEPARATOR);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid assignment pair: " + line);
        }
        Assignment first = Assignment.parse(line.substring(0, index));
        Assignment second = Assignment.parse(line.substring(index + 1));
        return new AssignmentPair(first, second);
    }
}
