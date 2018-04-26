package codewars;

public class TenPinBowling {
    static int bowling_score(String frames) {
        int score = 0;
        String[] fr = frames.split(" ", 10);
        int[] framesNumbers = parseStringToFrames(fr);

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
            if (currentFrame.contains("X")) {
                if (currentFrame.length() == 1) {
                    frames[currentThrow++] = 10;
                } else {
                    for(int pins : getFramePinNumbers(currentFrame)) {
                        frames[currentThrow++] = pins;
                    }
                }
            } else if (currentFrame.contains("/")) {
                int first = Integer.parseInt(currentFrame.substring(0, 1));
                frames[currentThrow++] = first;
                frames[currentThrow++] = 10 - first;
            } else {
                int combined = Integer.parseInt(currentFrame);
                frames[currentThrow++] = combined / 10;
                frames[currentThrow++] = combined % 10;
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
