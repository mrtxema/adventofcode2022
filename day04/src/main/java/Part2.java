import java.util.List;

public class Part2 {
    private final AssignmentService assignmentService;

    public Part2(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    public void run(List<AssignmentPair> assignmentPairs) {
        long anyOverlapCount = assignmentPairs.stream()
                .filter(assignmentService::isAnyOverlap)
                .count();
        System.out.printf("The count of assignment pairs that overlap at all is: %d%n", anyOverlapCount);
    }
}
