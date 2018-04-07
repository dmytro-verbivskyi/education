package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpinWordsTest {

    private String spin(String input) {
        return new SpinWords().spinWords(input);
    }

    @Test
    public void test() {
        assertThat(spin("Abcd")).isEqualTo("Abcd");
        assertThat(spin("Abcd efjh")).isEqualTo("Abcd efjh");
        assertThat(spin("Abcd      efjh")).isEqualTo("Abcd      efjh");

        assertThat(spin("Welcome")).isEqualTo("emocleW");
        assertThat(spin("Hey fellow warriors")).isEqualTo("Hey wollef sroirraw");
    }

}
