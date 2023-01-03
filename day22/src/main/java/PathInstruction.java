public interface PathInstruction {

    static PathInstruction parse(String spec) {
        if (Character.isDigit(spec.charAt(0))) {
            return new Move(Integer.parseInt(spec));
        }
        if (!spec.equals("R") && !spec.equals("L")) {
            throw new IllegalArgumentException("Invalid instruction: " + spec);
        }
        return new Turn(spec.equals("R"));
    }
}

record Move(int tiles) implements PathInstruction {
}

record Turn(boolean clockwise) implements PathInstruction {
}
