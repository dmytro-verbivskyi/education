package codewars;

public class Maskify {

    private static final char REPLACEMENT_SYMBOL = '#';
    private static final int VISIBLE_CHARS = 4;

    public static String maskify(String str) {
        if (str.length() <= VISIBLE_CHARS) {
            return str;
        }
        int L = str.length() - VISIBLE_CHARS;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < L; i++) {
            sb.append(REPLACEMENT_SYMBOL);
        }
        return sb.append(str.substring(L)).toString();
    }
}
