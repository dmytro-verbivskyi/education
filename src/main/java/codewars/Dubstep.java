package codewars;

import java.util.Arrays;
import java.util.stream.Collectors;

class Dubstep {
    static String songDecoder(String song) {
        String[] words = song.split("WUB");

        return Arrays.stream(words)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
