package codewars;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

class NextBiggerNumberWithTheSameDigits {

    static long nextBiggerNumber(long value) {
        List<Long> digits = getDigitsList(value);
        List<Long> variants = Lists.newArrayList();

        for (List<Long> variant : Collections2.permutations(digits)) {
            double next = generateDigit(variant);
            if (next > value) {
                variants.add((long) next);
            }
        }
        if (variants.isEmpty()) {
            return -1;
        }
        Collections.sort(variants);
        return variants.get(0);
    }

    private static List<Long> getDigitsList(long value) {
        List<Long> digits = Lists.newArrayList();
        while (value / 10 > 0) {
            digits.add(value % 10);
            value = value / 10;
        }
        digits.add(value % 10);
        return digits;
    }

    private static double generateDigit(List<Long> digits) {
        double number = 0.0;
        for (int i = 0; i < digits.size(); i++) {
            number += digits.get(i) * Math.pow(10, i);
        }
        return number;
    }

}
