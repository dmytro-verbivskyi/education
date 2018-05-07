package codewars;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class SimpleEncryptIndexDifference {

    private static char[] index =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,:;-?! '()$%&\"".toCharArray();
    private static Map<Character, Integer> indexMap = new HashMap<>();

    static {
        for (int i = 0; i < index.length; i++) {
            indexMap.put(index[i], i);
        }
    }

    static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        validate(text);
        char[] stepOne = stepOneEncrypt(text.toCharArray());
        char[] stepTwo = stepTwoEncrypt(stepOne);
        stepThree(stepTwo);
        return new String(stepTwo);
    }

    static String decrypt(String encrypted) {
        if (encrypted == null || encrypted.isEmpty()) {
            return encrypted;
        }
        validate(encrypted);
        char[] input = encrypted.toCharArray();
        stepThree(input);
        char[] stepTwo = stepTwoDecrypt(input);

        return new String(stepOneEncrypt(stepTwo));
    }

    private static void validate(String text) {
        char[] textChars = text.toCharArray();

        for (char c : textChars) {
            if (!indexMap.containsKey(c)) {
                throw new IllegalArgumentException("there not allowed chars");
            }
        }
    }

    private static char[] stepOneEncrypt(char[] text) {
        char[] stepOneResult = new char[text.length];

        for (int i = 0; i < text.length; i++) {
            if (i % 2 != 0) {
                stepOneResult[i] = isUpperCase(text[i]) ? toLowerCase(text[i]) : toUpperCase(text[i]);
            } else {
                stepOneResult[i] = text[i];
            }
        }
        return stepOneResult;
    }

    private static char[] stepTwoEncrypt(char[] stepOne) {
        char[] stepTwo = new char[stepOne.length];
        char[] original = stepOne;
        return stepTwoModification(original, stepOne, stepTwo);
    }

    private static char[] stepTwoDecrypt(char[] stepOne) {
        char[] stepTwo = new char[stepOne.length];
        char[] original = stepTwo;
        return stepTwoModification(original, stepOne, stepTwo);
    }

    private static char[] stepTwoModification(char[] original, char[] stepOne, char[] stepTwo) {
        stepTwo[0] = stepOne[0];

        for (int i = 0; i < stepOne.length - 1; i++) {
            int encId = (indexMap.get(original[i]) - indexMap.get(stepOne[i + 1]) + index.length) % index.length;
            stepTwo[i + 1] = index[encId];
        }
        return stepTwo;
    }

    private static void stepThree(char[] stepTwo) {
        stepTwo[0] = index[index.length - 1 - indexMap.get(stepTwo[0])];
    }

}
