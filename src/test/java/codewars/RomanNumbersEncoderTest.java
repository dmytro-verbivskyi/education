package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RomanNumbersEncoderTest {

    private RomanNumbersEncoder conversion = new RomanNumbersEncoder();

    @Test
    public void covertToRoman() {
        assertThat(conversion.solution(1)).isEqualTo("I");
        assertThat(conversion.solution(4)).isEqualTo("IV");
        assertThat(conversion.solution(7)).isEqualTo("VII");

        assertThat(conversion.solution(10)).isEqualTo("X");
        assertThat(conversion.solution(12)).isEqualTo("XII");
        assertThat(conversion.solution(19)).isEqualTo("XIX");
        assertThat(conversion.solution(23)).isEqualTo("XXIII");
        assertThat(conversion.solution(33)).isEqualTo("XXXIII");
        assertThat(conversion.solution(39)).isEqualTo("XXXIX");
        assertThat(conversion.solution(42)).isEqualTo("XLII");
        assertThat(conversion.solution(49)).isEqualTo("XLIX");
        assertThat(conversion.solution(50)).isEqualTo("L");
        assertThat(conversion.solution(98)).isEqualTo("XCVIII");
        assertThat(conversion.solution(99)).isEqualTo("XCIX");

        assertThat(conversion.solution(207)).isEqualTo("CCVII");
        assertThat(conversion.solution(400)).isEqualTo("CD");

        assertThat(conversion.solution(1066)).isEqualTo("MLXVI");
        assertThat(conversion.solution(1666)).isEqualTo("MDCLXVI");
        assertThat(conversion.solution(1990)).isEqualTo("MCMXC");
        assertThat(conversion.solution(2008)).isEqualTo("MMVIII");
        assertThat(conversion.solution(3999)).isEqualTo("MMMCMXCIX");
    }
}
