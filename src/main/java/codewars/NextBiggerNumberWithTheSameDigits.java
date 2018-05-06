package codewars;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class NextBiggerNumberWithTheSameDigits {

    static class GoodVariants {
        private List<List<Long>> variants = Lists.newArrayList();
        private Integer bestDigitLevel;

        GoodVariants(int newBestFoundLevel) {
            setNewBestDigitLevel(newBestFoundLevel);
        }

        void setNewBestDigitLevel(int newBestFoundLevel) {
            this.bestDigitLevel = newBestFoundLevel;
            variants.clear(); // forgetting solutions that were worse
        }
    }

    static long nextBiggerNumber(long value) {
        List<Long> digits = getDigitsList(value);
        Collection<List<Long>> permutationsWithoutDuplicates = Collections2.orderedPermutations(digits);

        GoodVariants goodVariants = new GoodVariants(digits.size() - 1);

        for (List<Long> currentVariant : permutationsWithoutDuplicates) {
            if (isItBetter(currentVariant, digits, goodVariants)) {
                goodVariants.variants.add(currentVariant);
            }
        }
        return goodVariants.variants.stream()
                .map(longs -> (long) generateDigit(longs))
                .sorted()
                .findFirst().orElse(-1L);
    }

    private static boolean isItBetter(List<Long> variant, List<Long> originalDigit, GoodVariants goodVariants) {
        for (int i = variant.size() - 1; i >= 0; i--) {
            if (variant.get(i) < originalDigit.get(i)) {
                return false;
            }
            if (!variant.get(i).equals(originalDigit.get(i))) {
                if (i > goodVariants.bestDigitLevel) {
                    return false;
                } else if (i == goodVariants.bestDigitLevel) {
                    return true;
                }
                goodVariants.setNewBestDigitLevel(i);
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
//=========================================================================================
    // This solution shows 5 times better performance
    static long nextBiggerNumberBestPerformance(long n) {
        char[] digits = String.valueOf(n).toCharArray();
        for (int i = digits.length-1; i > 0; i--) {
            if (digits[i] > digits[i-1]) {
                int nBiggerIndex = i;
                for (int j = digits.length-1; j > i; j--) {
                    if (digits[j] > digits[i-1]) {
                        nBiggerIndex = j;
                        break;
                    }
                }
                char temp = digits[nBiggerIndex];
                digits[nBiggerIndex] = digits[i-1];
                digits[i-1] = temp;
                Arrays.sort(digits, i, digits.length);
                return Long.parseLong(new String(digits));
            }
        }
        return -1;
    }

}
