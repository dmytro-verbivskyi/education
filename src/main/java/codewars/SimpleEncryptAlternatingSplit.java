package codewars;

class SimpleEncryptAlternatingSplit {

    static String encrypt(final String text, final int n) {
        if (n <= 0 || text == null) {
            return text;
        }
        int realN = n % 4;
        char[] result = text.toCharArray();
        char[] one = new char[text.length() - (text.length() / 2)];
        char[] two = new char[text.length() / 2];

        for (int times = 0; times < realN; times++) {
            for (int i = 0, odd = 0, even = 0; i < result.length; i++) {
                if (i % 2 == 0) {
                    one[odd++] = result[i];
                } else {
                    two[even++] = result[i];
                }
            }
            System.arraycopy(two, 0, result, 0, two.length);
            System.arraycopy(one, 0, result, two.length, one.length);
        }
        return new String(result);
    }

    static String decrypt(final String encryptedText, final int n) {
        if (n <= 0 || encryptedText == null) {
            return encryptedText;
        }
        int realN = n % 4;
        char[] result = encryptedText.toCharArray();
        char[] one = new char[encryptedText.length() / 2];
        char[] two = new char[encryptedText.length() - (encryptedText.length() / 2)];

        for (int times = 0; times < realN; times++) {
            System.arraycopy(result, 0, one, 0, one.length);
            System.arraycopy(result, one.length, two, 0, two.length);

            for (int i = 0, odd = 0, even = 0; i < result.length; i++) {
                if (i % 2 == 0) {
                    result[i] = two[even++];
                } else {
                    result[i] = one[odd++];
                }
            }
        }
        return new String(result);
    }

}
