import java.util.Arrays;

public enum Instruction {
    ADDX("addx", 2),
    NOOP("noop", 1);

    private final String code;
    private final int cycles;

    Instruction(String code, int cycles) {
        this.code = code;
        this.cycles = cycles;
    }

    public static Instruction parse(String spec) {
        return Arrays.stream(Instruction.values()).filter(i -> i.code.equals(spec)).findAny().orElseThrow();
    }

    public int getCycles() {
        return cycles;
    }
}
