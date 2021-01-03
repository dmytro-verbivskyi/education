package coderbyte;

import java.util.HashMap;
import java.util.Scanner;

import static java.util.Objects.isNull;

public class MinWindowSubstring {

    public static String MinWindowSubstring(String... strArr) {
        String full = strArr[0];
        String query = strArr[1];

        HashMap<Character, Integer> queryMap = composeQueryCharsMap(query);
        int lastPossibleMatch = full.length() - query.length() + 1;
        String bestMinWindowSubstring = full;

        for (int start = 0; start < lastPossibleMatch; start++) {
            char charStart = full.charAt(start);
            HashMap<Character, Integer> iterQueryMap = new HashMap<>(queryMap);
            int iterQueryLength = query.length();

            if (!iterQueryMap.containsKey(charStart)) {
                continue;
            }
            decrementAmount(charStart, iterQueryMap);
            iterQueryLength--;

            for (int end = start + 1; end < full.length() || iterQueryLength == 0; end++) {
                if (0 == iterQueryLength) {
                    if (end - start < bestMinWindowSubstring.length()) {
                        bestMinWindowSubstring = full.substring(start, end);
                    }
                    break;
                }

                char charEnd = full.charAt(end);

                if (iterQueryMap.containsKey(charEnd)) {
                    boolean charCodeWasStillPresent = decrementAmount(charEnd, iterQueryMap);

                    if (charCodeWasStillPresent) {
                        iterQueryLength--;
                    }
                    if (end - start > bestMinWindowSubstring.length()) {
                        break;
                    }
                }
            }
            if (bestMinWindowSubstring.length() == query.length()) {
                break; // no sense to continue, it's the best
            }
        }
        return bestMinWindowSubstring;
    }

    private static boolean decrementAmount(char charI, HashMap<Character, Integer> queryMap) {
        Integer amount = queryMap.get(charI);

        if (isNull(amount) || amount.equals(0)) {
            return false;
        }
        amount -= 1;
        queryMap.put(charI, amount);
        return true;
    }

    private static HashMap<Character, Integer> composeQueryCharsMap(String query) {
        HashMap<Character, Integer> queryMap = new HashMap<>();
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);

            Integer amount = 1;
            if (queryMap.containsKey(c)) {
                amount += queryMap.get(c);
            }
            queryMap.put(c, amount);
        }
        return queryMap;
    }

    public static void main(String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(MinWindowSubstring(s.nextLine()));
    }

}

