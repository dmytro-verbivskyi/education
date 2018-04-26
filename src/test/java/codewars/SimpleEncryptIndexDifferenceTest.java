package codewars;

import org.junit.Test;

import java.util.UUID;

import static codewars.SimpleEncryptIndexDifference.decrypt;
import static codewars.SimpleEncryptIndexDifference.encrypt;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleEncryptIndexDifferenceTest {

    @Test
    public void randomTest() {
        String uuidStr = UUID.randomUUID().toString();
        String encrypted = encrypt(uuidStr);

        assertThat(decrypt(encrypted)).isEqualTo(uuidStr);
    }

    @Test
    public void nullTest() {
        assertThat(encrypt(null)).isEqualTo(null);
    }

    @Test
    public void emptyTest() {
        assertThat(encrypt("")).isEqualTo("");
    }

    @Test
    public void oneCharTest() {
        assertThat(encrypt("C")).isEqualTo("%");
    }

    @Test
    public void twoCharsTest() {
        assertThat(encrypt("C4")).isEqualTo("%X");
    }

    @Test
    public void repeatingTest() {
        assertThat(encrypt("DDDDDD")).isEqualTo("$zazaz");
        assertThat(encrypt("DDADDA")).isEqualTo("$zdwa2");
    }

    @Test
    public void businessTest() {
        assertThat(encrypt("Business")).isEqualTo("&61kujla");
    }

    @Test
    public void thisIsTheTest() {
        assertThat(encrypt("This is a test!")).isEqualTo("5MyQa9p0riYplZc");
    }

    @Test
    public void thisKataIsVeryInteresting() {
        assertThat(encrypt("This kata is very interesting!"))
                .isEqualTo("5MyQa79H'ijQaw!Ns6jVtpmnlZ.V6p");
    }

    @Test
    public void kobayashiMaruTest() {
        assertThat(encrypt("Do the kata \"Kobayashi-Maru-Test!\" Endless fun and excitement when finding a solution!"))
                .isEqualTo("$-Wy,dM79H'i'o$n0C&I.ZTcMJw5vPlZc Hn!krhlaa:khV mkL;gvtP-S7Rt1Vp2RV:wV9VuhO Iz3dqb.U0w");
    }

    @Test
    public void businessDecryptTest() {
        assertThat(decrypt("&61kujla")).isEqualTo("Business");
    }

    @Test
    public void thisIsTheTestDecrypt() {
        assertThat(decrypt("5MyQa9p0riYplZc")).isEqualTo("This is a test!");
    }

    @Test
    public void thisKataIsVeryInterestingDecrypt() {
        assertThat(decrypt("5MyQa79H'ijQaw!Ns6jVtpmnlZ.V6p"))
                .isEqualTo("This kata is very interesting!");
    }

    @Test
    public void kobayashiMaruDecryptTest() {
        assertThat(decrypt("$-Wy,dM79H'i'o$n0C&I.ZTcMJw5vPlZc Hn!krhlaa:khV mkL;gvtP-S7Rt1Vp2RV:wV9VuhO Iz3dqb.U0w"))
                .isEqualTo("Do the kata \"Kobayashi-Maru-Test!\" Endless fun and excitement when finding a solution!");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCharShouldThrowError() {
        encrypt("char '@' is not allowed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void decryptInvalidCharShouldThrowError() {
        decrypt("char '@' is not allowed");
    }
}
