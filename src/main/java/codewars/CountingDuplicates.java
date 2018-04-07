package codewars;

import java.util.HashMap;

public class CountingDuplicates {
    public static int duplicateCount(String input) {
        HashMap<Integer, Integer> charAndVal = new HashMap<>();

        input.toLowerCase().chars().forEach(value ->
                charAndVal.merge(value, 1, (a, b) -> a + b)
        );
        return (int) charAndVal.entrySet().stream()
                .filter(v -> v.getValue() > 1)
                .count();
    }
}
