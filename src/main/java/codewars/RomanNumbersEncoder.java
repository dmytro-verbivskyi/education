package codewars;

import java.util.ArrayList;
import java.util.List;

class RomanNumbersEncoder {

    private static String[][] literals = new String[][]{
            {"",     "",     "",     ""},     // 0
            {"I",    "X",    "C",    "M"},    // 1
            {"II",   "XX",   "CC",   "MM"},   // 2
            {"III",  "XXX",  "CCC",  "MMM"},  // 3
            {"IV",   "XL",   "CD",   null},   // 4
            {"V",    "L",    "D",    null},   // 5
            {"VI",   "LX",   "DC",   null},   // 6
            {"VII",  "LXX",  "DCC",  null},   // 7
            {"VIII", "LXXX", "DCCC", null},   // 8
            {"IX",   "XC",   "CM",   null}    // 9
    };

    String solution(int value) {
        List<Integer> digits = new ArrayList<>();
        while (value / 10 > 0) {
            digits.add(value % 10);
            value = value / 10;
        }
        digits.add(value % 10);

        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < digits.size(); pos++) {
            sb.insert(0, literals[digits.get(pos)][pos]);
        }
        return sb.toString();
    }
}
