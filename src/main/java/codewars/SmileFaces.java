package codewars;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmileFaces {

    public static int countSmileys(List<String> input) {
        int smileys = 0;

        for (String word : input) {
            smileys += hasCompleteSmileyStr(word);
        }
        return smileys;
    }

    static Set<String> smileys = new HashSet<String>() {
        {
            addAll(Arrays.asList(
                    ":)", ":D", ":-)", ":-D", ":~)", ":~D",
                    ";)", ";D", ";-)", ";-D", ";~)", ";~D"
            ));
        }
    };

    private static int hasCompleteSmileyStr(String word) {
        return smileys.contains(word) ? 1 : 0;
    }

    private static int hasCompleteSmiley(String word) {
        if (word.length() < 2 || word.length() > 3) {
            return 0;
        }
        if (!hasEyes(word.charAt(0))) {
            return 0;
        }

        boolean nose = hasNose(word.charAt(1));
        boolean mouth = hasMouth(word.charAt(1));
        if (mouth && word.length() == 2) {
            return 1;
        }
        if (!nose) {
            return 0;
        }

        if (2 < word.length()) {
            mouth = hasMouth(word.charAt(2));
        }
        if (mouth) {
            return 1;
        }
        return 0;
    }

    private static boolean hasMouth(int charValue) {
        return (charValue == 41 || charValue == 68);
    }

    private static boolean hasNose(int charValue) {
        return (charValue == 45 || charValue == 126);
    }

    private static boolean hasEyes(int charValue) {
        return (charValue == 58 || charValue == 59);
    }
}
