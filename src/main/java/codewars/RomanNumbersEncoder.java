package codewars;

import java.util.ArrayList;
import java.util.List;

public class RomanNumbersEncoder {

    public String solution(int value) {
        List<Integer> arr = new ArrayList<>();
        while (value / 10 > 0) {
            arr.add(value % 10);
            value = value / 10;
        }
        arr.add(value % 10);

        StringBuilder sb = new StringBuilder();

        for (Integer n : arr) {
            switch (n) {
                case 1: sb.append("I"); break;
                case 2: sb.append("II"); break;
                case 3: sb.append("III"); break;
                case 4: sb.append("IV"); break;
                case 5: sb.append("V"); break;
                case 6: sb.append("VI"); break;
                case 7: sb.append("VII"); break;
                case 8: sb.append("VIII"); break;
                case 9: sb.append("IX"); break;
            }
        }

        return sb.toString();
    }
}
