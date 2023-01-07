public class SnafuNumber {
    private final String value;

    private SnafuNumber(String value) {
        this.value = value;
    }

    public static SnafuNumber valueOf(String spec) {
        return new SnafuNumber(spec);
    }

    public static SnafuNumber fromLong(long totalFuel) {
        StringBuilder result = new StringBuilder();
        long pending = totalFuel;
        while (pending != 0) {
            int nextDigit = (int) (pending % SnafuDigit.SNAFU_BASE);
            SnafuDigit nextSnafuDigit = SnafuDigit.fromCongruentValue(nextDigit);
            result.insert(0, nextSnafuDigit.getSymbol());
            pending = (pending - nextSnafuDigit.getValue()) / SnafuDigit.SNAFU_BASE;
        }
        return new SnafuNumber(result.toString());
    }

    public long longValue() {
        long result = 0;
        long factor = 1;
        for (int i = value.length() - 1; i >= 0; i--) {
            result += SnafuDigit.parse(value.charAt(i)).getValue() * factor;
            factor *= SnafuDigit.SNAFU_BASE;
        }
        return result;
    }

    @Override
    public String toString() {
        return value;
    }
}
