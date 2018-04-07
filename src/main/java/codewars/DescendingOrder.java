package codewars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DescendingOrder {

    // my is faster
    public static int sortDescMy(final int num) {
        int value = num;

        List<Integer> arr = new ArrayList<>();
        while (value / 10 > 0) {
            arr.add(value % 10);
            value = value / 10;
        }
        arr.add(value % 10);
        Collections.sort(arr);

        StringBuilder result = new StringBuilder();
        for (int i = arr.size() - 1; i >= 0; i--) {
            result.append(arr.get(i));
        }
        return Integer.parseInt(result.toString());
    }

    public static int sortDesc(final int num) {
        return Integer.parseInt(String.valueOf(num)
                .chars()
                .mapToObj(i -> String.valueOf(Character.getNumericValue(i)))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining()));
    }
}
