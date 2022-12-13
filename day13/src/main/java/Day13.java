import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day13 {
    private static final String INPUT_FILE_NAME = "input.txt";
    private static final String TEST_FILE_NAME = "test.txt";

    public static void main(String[] args) {
        try {
            new Day13().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PacketParser parser = new PacketParser();
        List<Packet> packets = readInputFile(INPUT_FILE_NAME, parser);
        ItemComparator comparator = new ItemComparator();

        int rightSum = PacketPair.parse(packets).stream()
                .filter(pair -> comparator.compare(pair.left().list(), pair.right().list()) < 0)
                .mapToInt(PacketPair::index).sum();
        System.out.println("Part 1. The sum of indices of the pairs in right order is: " + rightSum);

        List<Packet> dividerPackets = List.of(parser.parse("[[2]]"), parser.parse("[[6]]"));
        List<Packet> sortedPackets = Stream.concat(packets.stream(), dividerPackets.stream())
                .sorted(Comparator.comparing(Packet::list, comparator))
                .toList();
        int decoderKey = dividerPackets.stream().mapToInt(divider -> sortedPackets.indexOf(divider) + 1).reduce(1, (a, b) -> a * b);
        System.out.println("Part 2. The decoderKey is: " + decoderKey);
    }

    private List<Packet> readInputFile(String fileName, PacketParser parser) {
        try {
            return IOUtils.readTrimmedLines(getClass().getResource(fileName)).stream()
                    .filter(line -> !line.isEmpty())
                    .map(parser::parse)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
