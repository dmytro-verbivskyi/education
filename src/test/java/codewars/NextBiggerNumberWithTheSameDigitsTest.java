package codewars;

import org.junit.Test;

import static codewars.NextBiggerNumberWithTheSameDigits.nextBiggerNumber;
import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberWithTheSameDigitsTest {

    @Test
    public void wrongVariants() {
        assertThat(nextBiggerNumber(9)).isEqualTo(-1);
        assertThat(nextBiggerNumber(111)).isEqualTo(-1);
        assertThat(nextBiggerNumber(513)).isEqualTo(-1);
    }

    @Test
    public void basicTests() {
        assertThat(nextBiggerNumber(12)).isEqualTo(21);
        assertThat(nextBiggerNumber(513)).isEqualTo(531);
        assertThat(nextBiggerNumber(2017)).isEqualTo(2071);
        assertThat(nextBiggerNumber(414)).isEqualTo(441);
        assertThat(nextBiggerNumber(144)).isEqualTo(414);
        assertThat(nextBiggerNumber(199)).isEqualTo(919);
    }

    @Test
    public void notSmallNumberTest() {
        assertThat(nextBiggerNumber(10010099)).isEqualTo(10090019);
    }
}
