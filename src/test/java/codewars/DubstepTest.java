package codewars;

import org.junit.Test;

import static codewars.Dubstep.songDecoder;
import static org.assertj.core.api.Assertions.assertThat;

public class DubstepTest {

    @Test
    public void test1() {
        assertThat(songDecoder("WUBWUBABCWUB")).isEqualTo("ABC");
    }

    @Test
    public void test2() {
        assertThat(songDecoder("RWUBWUBWUBLWUB")).isEqualTo("R L");
    }
}
