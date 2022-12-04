import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RucksackService {

    public char findRepeatedItemType(Rucksack rucksack) {
        Set<Character> repeatedChars = findRepeatedItemTypes(rucksack.firstCompartment(), rucksack.secondCompartment());
        if (repeatedChars.size() != 1) {
            throw new IllegalArgumentException("Rucksack with %d repeated item types: %s".formatted(repeatedChars.size(), rucksack.join()));
        }
        return repeatedChars.iterator().next();
    }

    public char findRepeatedItemType(List<Rucksack> rucksacks) {
        Set<Character> repeatedChars = findRepeatedItemTypes(rucksacks.stream().map(Rucksack::join).toList().toArray(new String[0]));
        if (repeatedChars.size() != 1) {
            throw new IllegalArgumentException("Rucksack group with %d repeated item types".formatted(repeatedChars.size()));
        }
        return repeatedChars.iterator().next();
    }

    private Set<Character> findRepeatedItemTypes(String... compartmentList) {
        if (compartmentList.length < 2) {
            throw new IllegalArgumentException("Too few items: " + compartmentList.length);
        }
        Set<Character> repeatedChars = compartmentList[0].chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        for (int i = 1; i < compartmentList.length; i++) {
            repeatedChars = findRepeatedChars(repeatedChars, compartmentList[i]);
        }
        return repeatedChars;
    }

    private Set<Character> findRepeatedChars(Set<Character> firstItemTypes, String secondCompartment) {
        return secondCompartment.chars()
                .mapToObj(c -> (char) c)
                .filter(firstItemTypes::contains)
                .collect(Collectors.toSet());
    }

    public int getItemTypePriority(char itemType) {
        if (Character.isLowerCase(itemType)) {
            return itemType - 'a' + 1;
        } else {
            return itemType - 'A' + 27;
        }
    }
}
