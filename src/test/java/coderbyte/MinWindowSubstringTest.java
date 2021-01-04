package coderbyte;

import org.junit.Test;

import static coderbyte.MinWindowSubstring.MinWindowSubstring;
import static org.assertj.core.api.Assertions.assertThat;

public class MinWindowSubstringTest {

    @Test
    public void bacba() {
        assertThat(MinWindowSubstring("bacba", "ab")).isEqualTo("ba");
    }

    @Test
    public void caabdccdbcacd() {
        assertThat(MinWindowSubstring("caabdccdbcacd", "aad")).isEqualTo("aabd");
    }

    @Test
    public void aaabaaddae() {
        assertThat(MinWindowSubstring("aaabaaddae", "aed")).isEqualTo("dae");
    }

    @Test
    public void ahffaksfajeeubsne() {
        assertThat(MinWindowSubstring("ahffaksfajeeubsne", "jefaa")).isEqualTo("aksfaje");
    }

    @Test
    public void aaffhkksemckelloe() {
        assertThat(MinWindowSubstring("aaffhkksemckelloe", "fhea")).isEqualTo("affhkkse");
    }

    @Test
    public void aaffsfsfasfasfasfasfasfacasfafe() {
        assertThat(MinWindowSubstring("aaffsfsfasfasfasfasfasfacasfafe", "fafsf")).isEqualTo("affsf");
    }

    @Test
    public void aaaaaaaaa() {
        assertThat(MinWindowSubstring("aaaaaaaaa", "a")).isEqualTo("a");
    }

    @Test
    public void vvavereveaevafefaef() {
        assertThat(MinWindowSubstring("vvavereveaevafefaef", "vvev")).isEqualTo("vvave");
    }

    @Test
    public void caae() {
        assertThat(MinWindowSubstring("caae", "cae")).isEqualTo("caae");
    }

    @Test
    public void cccaabbbbrr() {
        assertThat(MinWindowSubstring("cccaabbbbrr", "rbac")).isEqualTo("caabbbbr");
    }
}
