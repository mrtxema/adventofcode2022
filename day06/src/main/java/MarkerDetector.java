import java.util.List;

public class MarkerDetector {
    private final int markerLength;

    public MarkerDetector(int markerLength) {
        this.markerLength = markerLength;
    }

    public void run(List<String> lines) {
        lines.forEach(this::printStartMarkerPositionr);
    }

    private void printStartMarkerPositionr(String line) {
        int position = findStartMarker(line);
        System.out.printf("- %s%n  first marker after character %d%n", line, position);
    }

    private int findStartMarker(String line) {
        for (int i = markerLength; i <= line.length(); i++) {
            String lastPacket = line.substring(i - markerLength, i);
            if (isStartMarker(lastPacket)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Line doesn't include a start marker: " + line);
    }

    private boolean isStartMarker(String packet) {
        return packet.chars().distinct().count() == packet.length();
    }
}
