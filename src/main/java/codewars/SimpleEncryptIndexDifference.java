package codewars;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.*;

public class SimpleEncryptIndexDifference {

    private static char[] index =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,:;-?! '()$%&\"".toCharArray();
    private static Map<Character, Integer> indexMap = new HashMap<>();

    static {
        for (int i = 0; i < index.length; i++) {
            indexMap.put(index[i], i);
        }
    }

    String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        char[] stepOne = stepOneEncrypt(text.toCharArray());
        char[] stepTwo = stepTwoEncrypt(stepOne);
        stepThree(stepTwo);
        return new String(stepTwo);
    }

    String decrypt(String encrypted) {
        if (encrypted == null || encrypted.isEmpty()) {
            return encrypted;
        }
        char[] input = encrypted.toCharArray();
        stepThree(input);
        char[] stepTwo = stepTwoDecrypt(input);

        return new String(stepOneEncrypt(stepTwo));
    }

    private char[] stepOneEncrypt(char[] text) {
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

    private char[] stepTwoEncrypt(char[] stepOne) {
        char[] stepTwo = new char[stepOne.length];
        char[] original = stepOne;
        return stepTwoModification(original, stepOne, stepTwo);
    }

    private char[] stepTwoDecrypt(char[] stepOne) {
        char[] stepTwo = new char[stepOne.length];
        char[] original = stepTwo;
        return stepTwoModification(original, stepOne, stepTwo);
    }

    private char[] stepTwoModification(char[] original, char[] stepOne, char[] stepTwo) {
        stepTwo[0] = stepOne[0];

        for (int i = 0; i < stepOne.length - 1; i++) {
            int encId = (indexMap.get(original[i]) - indexMap.get(stepOne[i + 1]) + index.length) % index.length;
            stepTwo[i + 1] = index[encId];
        }
        return stepTwo;
    }

    private void stepThree(char[] stepTwo) {
        stepTwo[0] = index[index.length - 1 - indexMap.get(stepTwo[0])];
    }

}
