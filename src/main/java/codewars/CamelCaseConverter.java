package codewars;

import java.util.Arrays;
import java.util.stream.Collectors;

class CamelCaseConverter {

    static String toCamelCase(String input) {
        return toCamelCaseDeclarativeNotMy(input);
    }

    static String toCamelCaseDeclarativeNotMy(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String[] words = input.split("[-_]");

        /*               -joining-        -reduce with string.concat-
                         ---------               ---------
     heap size before:   126877696               126877696

      heap size after:   160432128               224395264
                         ---------               ---------
            were used:  33.554.432 bytes        97.517.568 bytes
         */
        /* 430 ms - for testVeryLongConversion() with 91 words*/
        /*return Arrays.stream(words, 1, words.length)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .reduce(words[0], String::concat);
        */

        /* 390 ms - for testVeryLongConversion() with 91 words*/
        return words[0].concat(Arrays.stream(words, 1, words.length)
                .filter(s -> !s.isEmpty())
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining())
        );
    }

    /* 85 ms - for testVeryLongConversion() with 91 words*/
    static String toCamelCaseMyImperative(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String[] parts = input.split("[-_]");
        StringBuilder output = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            String p = parts[i];

            if (p.isEmpty()) {
                continue;
            }
            output.append(p.substring(0, 1).toUpperCase());
            if (p.length() != 1) {
                output.append(p.substring(1));
            }
        }
        return output.toString();
    }
}
