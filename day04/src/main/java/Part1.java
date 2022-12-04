import java.util.List;

public class Part1 {
    private final AssignmentService assignmentService;

    public Part1(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    public void run(List<AssignmentPair> assignmentPairs) {
        long fullOverlapCount = assignmentPairs.stream()
                .filter(assignmentService::isFullOverlap)
                .count();
        System.out.printf("The count of assignment pairs that fully overlap is: %d%n", fullOverlapCount);
    }
}
