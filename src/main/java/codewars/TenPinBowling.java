package codewars;

public class TenPinBowling {

    static int bowling_score(String frames) {
        int score = 0;
        String[] frameStrings = frames.split(" ", 10);
        int[] framesNumbers = parseStringToFrames(frameStrings);

        for (int frame = 0, throwIndex = 0; frame < 10; frame++) {
            if (10 == framesNumbers[throwIndex]) {
                score += 10 + framesNumbers[throwIndex + 1] + framesNumbers[throwIndex + 2];
                throwIndex++;
            } else if (10 == framesNumbers[throwIndex] + framesNumbers[throwIndex + 1]) {
                score += 10 + framesNumbers[throwIndex + 2];
                throwIndex += 2;
            } else {
                score += framesNumbers[throwIndex] + framesNumbers[throwIndex + 1];
                throwIndex += 2;
            }
        }
        return score;
    }

    private static int[] parseStringToFrames(String[] framesStr) {
        int[] frames = new int[21];

        int currentThrow = 0;
        for (String currentFrame : framesStr) {
            for (int pins : getFramePinNumbers(currentFrame)) {
                frames[currentThrow++] = pins;
            }
        }
        return frames;
    }

    private static int[] getFramePinNumbers(String frameStr) {
        int[] framePins = new int[frameStr.length()];
        int current = 0;
        for (char c : frameStr.toCharArray()) {
            if (c == 'X') {
                framePins[current++] = 10;
            } else if (c == '/') {
                framePins[current] = 10 - framePins[current - 1];
                current++;
            } else {
                framePins[current++] = Character.getNumericValue(c);
            }
        }
        return framePins;
    }
}
