package codewars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;

class SortTheOdd {

    static int[] sortArray(int[] array) {
//        return myImperativeWay(array);
        return notMyDeclarativeWay(array);
    }

    private static int[] myImperativeWay(int[] array) {
        int oddIndex = 0;
        List<Integer> onlyOdds = new ArrayList<>();
        int[] positionsOfOdds = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            int number = array[i];

            if (number % 2 != 0) {
                onlyOdds.add(number);
                positionsOfOdds[oddIndex] = i;
                oddIndex++;
            }
        }
        Collections.sort(onlyOdds);

        for (int i = 0; i < oddIndex; i++) {
            array[positionsOfOdds[i]] = onlyOdds.get(i);
        }
        return array;
    }

    private static int[] notMyDeclarativeWay(int[] array) {
        OfInt sortedOdds = IntStream.of(array)
                .filter(i -> i % 2 != 0)
                .sorted()
                .iterator();

        return IntStream.of(array)
                .map(i -> i % 2 == 0 ? i : sortedOdds.nextInt())
                .toArray();
    }

}
