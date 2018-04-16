package codewars;

import java.util.Arrays;

class SortTheOdd {

    static int[] sortArray(int[] array) {
        int oddIndex = 0;
        int[] onlyOdds = new int[array.length];
        int[] positionsOfOdds = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            int number = array[i];

            if (number % 2 != 0) {
                onlyOdds[oddIndex] = number;
                positionsOfOdds[oddIndex] = i;
                oddIndex++;
            }
        }
        Arrays.sort(onlyOdds);

        for (int i = 0; i < oddIndex; i++) {

        }

        return array;
    }

}
