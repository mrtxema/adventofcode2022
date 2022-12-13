import java.util.List;
import java.util.stream.IntStream;

public record PacketPair(int index, Packet left, Packet right) {

    public static List<PacketPair> parse(List<Packet> packets) {
        if (packets.size() % 2 != 0) {
            throw new IllegalArgumentException("Missing packet");
        }
        return IntStream.range(0, packets.size() / 2)
                .mapToObj(index -> new PacketPair(index + 1, packets.get(index * 2), packets.get(index * 2 + 1)))
                .toList();
    }
}
