package codewars;

import org.junit.Test;

import static codewars.TenPinBowling.bowling_score;
import static org.assertj.core.api.Assertions.assertThat;

public class TenPinBowlingTest {

    @Test
    public void allZeroesTest() {
        assertThat(bowling_score("00 00 00 00 00 00 00 00 00 00")).isEqualTo(0);
    }

    @Test
    public void allOnesTest() {
        assertThat(bowling_score("11 11 11 11 11 11 11 11 11 11")).isEqualTo(20);
    }

    @Test
    public void oneSpareTest() {
        assertThat(bowling_score("3/ 30 00 00 00 00 00 00 00 00")).isEqualTo(16);
    }

    @Test
    public void oneStrikeTest() {
        assertThat(bowling_score("X 36 00 00 00 00 00 00 00 00")).isEqualTo(28);
    }

    @Test
    public void perfectGameTest() {
        assertThat(bowling_score("X X X X X X X X X XXX")).isEqualTo(300);
    }

    @Test
    public void lastChanceTest() {
        assertThat(bowling_score("00 00 00 00 00 00 00 00 00 9/X")).isEqualTo(20);
    }
}
