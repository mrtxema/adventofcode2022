public record Sentence(Instruction instruction, Integer argument) {

    public static Sentence parse(String spec) {
        String[] parts = spec.split(" +", 2);
        Integer argument = parts.length < 2 ? null : Integer.valueOf(parts[1]);
        return new Sentence(Instruction.parse(parts[0]), argument);
    }
}
