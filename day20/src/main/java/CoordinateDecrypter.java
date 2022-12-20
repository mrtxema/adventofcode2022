import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class CoordinateDecrypter {
    private static final long KEY = 811589153;
    private final List<Item> original;
    private final CircularList<Item> mixed;
    private final int mixTimes;

    private CoordinateDecrypter(List<Item> original, int mixTimes) {
        this.original = original;
        this.mixed = new CircularList<>(original);
        this.mixTimes = mixTimes;
    }

    public static CoordinateDecrypter newVariant1(List<Integer> encryptedCoordinates) {
        List<Item> items = IntStream.range(0, encryptedCoordinates.size())
                .mapToObj(index -> new Item(encryptedCoordinates.get(index), index))
                .toList();
        return new CoordinateDecrypter(items, 1);
    }

    public static CoordinateDecrypter newVariant2(List<Integer> encryptedCoordinates) {
        List<Item> items = IntStream.range(0, encryptedCoordinates.size())
                .mapToObj(index -> new Item(encryptedCoordinates.get(index) * KEY, index))
                .toList();
        return new CoordinateDecrypter(items, 10);
    }

    public LongStream decrypt() {
        IntStream.range(0, mixTimes).forEach(i -> original.forEach(this::mix));
        Item zeroItem = original.stream().filter(item -> item.getValue() == 0).findAny().orElseThrow();
        int baseIndex = mixed.indexOf(zeroItem);
        return IntStream.of(1000, 2000, 3000).mapToObj(index -> mixed.get(baseIndex + index)).mapToLong(Item::getValue);
    }

    private void mix(Item item) {
        int currentIndex = mixed.indexOf(item);
        mixed.moveWithOffset(currentIndex, item.getValue());
    }

    private static class Item {
        final long value;
        final int originalIndex;

        public Item(long value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }

        public long getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Item other && originalIndex == other.originalIndex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(originalIndex);
        }
    }
}
