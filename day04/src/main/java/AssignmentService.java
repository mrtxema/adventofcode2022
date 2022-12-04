public class AssignmentService {

    public boolean isFullOverlap(AssignmentPair assignmentPair) {
        return assignmentPair.firstAssignment().contains(assignmentPair.secondAssignment()) ||
                assignmentPair.secondAssignment().contains(assignmentPair.firstAssignment());
    }

    public boolean isAnyOverlap(AssignmentPair assignmentPair) {
        return assignmentPair.firstAssignment().overlaps(assignmentPair.secondAssignment());
    }
}
