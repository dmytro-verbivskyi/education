package codewars;

import java.util.Arrays;
import java.util.List;

public class SpinWords {

    public String spinWords(String input) {
        if (input.length() < 5) {
            return input;
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer oneWordBuffer = new StringBuffer();
        List<String> words = Arrays.asList(input.split(" "));

        for (String word : words) {
            if (word.length() > 4) {
                sb.append(oneWordBuffer.append(word).reverse());
                oneWordBuffer.delete(0, word.length());
            } else {
                sb.append(word);
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
