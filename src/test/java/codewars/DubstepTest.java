package codewars;

import org.junit.Test;

import static codewars.Dubstep.SongDecoder;
import static org.assertj.core.api.Assertions.assertThat;

public class DubstepTest {

    @Test
    public void Test1() {
        assertThat(SongDecoder("WUBWUBABCWUB")).isEqualTo("ABC");
    }

    @Test
    public void Test2() {
        assertThat(SongDecoder("RWUBWUBWUBLWUB")).isEqualTo("R L");
    }
}
