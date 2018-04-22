package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleEncryptIndexDifferenceTest {

    SimpleEncryptIndexDifference indexDifference = new SimpleEncryptIndexDifference();

    @Test
    public void nullTest() {
        assertThat(indexDifference.encrypt(null)).isEqualTo(null);
    }

    @Test
    public void emptyTest() {
        assertThat(indexDifference.encrypt("")).isEqualTo("");
    }

    @Test
    public void oneCharTest() {
        assertThat(indexDifference.encrypt("C")).isEqualTo("%");
    }

    @Test
    public void twoCharsTest() {
        assertThat(indexDifference.encrypt("C4")).isEqualTo("%X");
    }

    @Test
    public void repeatingTest() {
        assertThat(indexDifference.encrypt("DDDDDD")).isEqualTo("$zazaz");
        assertThat(indexDifference.encrypt("DDADDA")).isEqualTo("$zdwa2");
    }

    @Test
    public void businessTest() {
        assertThat(indexDifference.encrypt("Business")).isEqualTo("&61kujla");
    }

    @Test
    public void thisIsTheTest() {
        assertThat(indexDifference.encrypt("This is a test!")).isEqualTo("5MyQa9p0riYplZc");
    }

    @Test
    public void thisKataIsVeryInteresting() {
        assertThat(indexDifference
                .encrypt("This kata is very interesting!"))
                .isEqualTo("5MyQa79H'ijQaw!Ns6jVtpmnlZ.V6p");
    }

    @Test
    public void kobayashiMaruTest() {
        assertThat(indexDifference
                .encrypt("Do the kata \"Kobayashi-Maru-Test!\" Endless fun and excitement when finding a solution!"))
                .isEqualTo("$-Wy,dM79H'i'o$n0C&I.ZTcMJw5vPlZc Hn!krhlaa:khV mkL;gvtP-S7Rt1Vp2RV:wV9VuhO Iz3dqb.U0w");
    }

    @Test
    public void businessDecryptTest() {
        assertThat(indexDifference.decrypt("&61kujla")).isEqualTo("Business");
    }

    @Test
    public void thisIsTheTestDecrypt() {
        assertThat(indexDifference.decrypt("5MyQa9p0riYplZc")).isEqualTo("This is a test!");
    }

    @Test
    public void thisKataIsVeryInterestingDecrypt() {
        assertThat(indexDifference
                .decrypt("5MyQa79H'ijQaw!Ns6jVtpmnlZ.V6p"))
                .isEqualTo("This kata is very interesting!");
    }

    @Test
    public void kobayashiMaruDecryptTest() {
        assertThat(indexDifference
                .decrypt("$-Wy,dM79H'i'o$n0C&I.ZTcMJw5vPlZc Hn!krhlaa:khV mkL;gvtP-S7Rt1Vp2RV:wV9VuhO Iz3dqb.U0w"))
                .isEqualTo("Do the kata \"Kobayashi-Maru-Test!\" Endless fun and excitement when finding a solution!");
    }

}
