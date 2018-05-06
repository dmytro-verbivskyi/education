package codewars;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

class NextBiggerNumberWithTheSameDigits {

    static class GoodVariants {
        private List<List<Long>> variants;
        private Integer bestDigitLevel;

        void setBestDigitLevel(int newBestFoundLevel) {
            this.bestDigitLevel = newBestFoundLevel;
            variants = Lists.newArrayList();
        }
    }

    static long nextBiggerNumber(long value) {
        List<Long> digits = getDigitsList(value);
        Collection<List<Long>> permutationsWithoutDuplicates = Collections2.orderedPermutations(digits);

        List<Long> variants = Lists.newArrayList();
        GoodVariants goodVariants = new GoodVariants();
        goodVariants.setBestDigitLevel(digits.size() - 1);

        for (List<Long> currentVariant : permutationsWithoutDuplicates) {
            if (isPossibleGreater(currentVariant, digits, goodVariants)) {
                goodVariants.variants.add(currentVariant);
            }
        }
        for (List<Long> currentVariant : goodVariants.variants) {
            double next = generateDigit(currentVariant);
            variants.add((long) next);
        }

        if (variants.isEmpty()) {
            return -1;
        }
        Collections.sort(variants);
        return variants.get(0);
    }

    private static boolean isPossibleGreater(List<Long> variant, List<Long> digits, GoodVariants goodVariants) {
        for (int i = variant.size() - 1; i >= 0; i--) {
            if (variant.get(i) < digits.get(i)) {
                return false;
            }
            if (variant.get(i) != digits.get(i)) {
                if (i > goodVariants.bestDigitLevel) {
                    return false;
                } else if (i == goodVariants.bestDigitLevel) {
                    return true;
                }
                goodVariants.setBestDigitLevel(i);
                return true;
            }
        }
        return false;
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
