import java.util.List;

public class ElvesMapEvaluator {
    private final ElvesMap map;

    public ElvesMapEvaluator(ElvesMap map) {
        this.map = map;
    }

    public ElvesMap evaluate(int rounds) {
        ElvesMap liveMap = map;
        for (int i = 0; i < rounds; i++) {
            liveMap = evaluateRound(liveMap, i);
        }
        return liveMap;
    }

    public int countRoundsUntilNoMoves() {
        ElvesMap previousMap = null;
        ElvesMap liveMap = map;
        int round = 0;
        while (!liveMap.equals(previousMap)) {
            previousMap = liveMap;
            liveMap = evaluateRound(previousMap, round++);
        }
        return round;
    }

    private ElvesMap evaluateRound(ElvesMap liveMap, int round) {
        List<Elf> proposals = liveMap.getElves().stream().map(elf -> getPositionProposal(elf, liveMap, round)).toList();
        return liveMap.applyProposals(proposals);
    }

    private Elf getPositionProposal(Elf elf, ElvesMap liveMap, int round) {
        if (elf.position().getAllAdjacents().noneMatch(liveMap::isAnyElf)) {
            return elf;
        }
        return Direction.getAllFrom(round % 4)
                .filter(direction -> elf.position().getAdjacents(direction).noneMatch(liveMap::isAnyElf))
                .map(direction -> elf.position().move(direction))
                .map(position -> new Elf(elf.id(), position))
                .findFirst()
                .orElse(elf);
    }
}
