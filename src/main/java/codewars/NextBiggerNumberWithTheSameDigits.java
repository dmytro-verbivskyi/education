package codewars;

import java.util.ArrayList;
import java.util.List;

class NextBiggerNumberWithTheSameDigits {

    static long nextBiggerNumber(long value) {
        List<Long> digits = new ArrayList<>();
        while (value / 10 > 0) {
            digits.add(value % 10);
            value = value / 10;
        }
        digits.add(value % 10);

        /*
        1234
        1243
        1423
        1432
        1342
        1324

        3124
        3142
        3412
        3421
        3241
        3214

        2314
        2341
        2431
        2413
        2143
        2134

        --
        4132
        4123
        4213
        4231
        4321
        4312
         */


        return -1;
    }

}
