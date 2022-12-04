public record Assignment(int startId, int endId) {
    private static final char SEPARATOR = '-';

    public static Assignment parse(String assignmentSpec) {
        int index = assignmentSpec.indexOf(SEPARATOR);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid assignment: " + assignmentSpec);
        }
        int first = Integer.parseInt(assignmentSpec.substring(0, index));
        int second = Integer.parseInt(assignmentSpec.substring(index + 1));
        if (first > second) {
            throw new IllegalArgumentException("Invalid assignment ids: %d, %d".formatted(first, second));
        }
        return new Assignment(first, second);
    }

    public boolean contains(Assignment other) {
        return startId <= other.startId() && endId >= other.endId();
    }

    public boolean overlaps(Assignment other) {
        // The intersection is not empty
        return Math.max(startId, other.startId()) <= Math.min(endId, other.endId());
    }
}
