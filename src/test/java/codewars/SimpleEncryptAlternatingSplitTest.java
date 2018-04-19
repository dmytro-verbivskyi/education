package codewars;

import org.junit.Test;

import static codewars.SimpleEncryptAlternatingSplit.decrypt;
import static codewars.SimpleEncryptAlternatingSplit.encrypt;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleEncryptAlternatingSplitTest {

    @Test
    public void testEncrypt() {
        assertThat(encrypt("This is a test!", -1)).isEqualTo("This is a test!");
        assertThat(encrypt("This is a test!", 0)).isEqualTo("This is a test!");
        assertThat(encrypt("This is a test!", 1)).isEqualTo("hsi  etTi sats!");
        assertThat(encrypt("This is a test!", 2)).isEqualTo("s eT ashi tist!");
        assertThat(encrypt("This is a test!", 3)).isEqualTo(" Tah itse sits!");
        assertThat(encrypt("This is a test!", 4)).isEqualTo("This is a test!");
        assertThat(encrypt("This kata is very interesting!", 1)).isEqualTo("hskt svr neetn!Ti aai eyitrsig");
    }

    @Test
    public void testDecrypt() {
        assertThat(decrypt("This is a test!", 0)).isEqualTo("This is a test!");
        assertThat(decrypt("hsi  etTi sats!", 1)).isEqualTo("This is a test!");
        assertThat(decrypt("s eT ashi tist!", 2)).isEqualTo("This is a test!");
        assertThat(decrypt(" Tah itse sits!", 3)).isEqualTo("This is a test!");
        assertThat(decrypt("This is a test!", 4)).isEqualTo("This is a test!");
        assertThat(decrypt("This is a test!", -1)).isEqualTo("This is a test!");
        assertThat(decrypt("hskt svr neetn!Ti aai eyitrsig", 1)).isEqualTo("This kata is very interesting!");
    }

    @Test
    public void testNullOrEmpty() {
        assertThat(encrypt("", 0)).isEmpty();
        assertThat(decrypt("", 0)).isEmpty();
        assertThat(encrypt(null, 0)).isNull();
        assertThat(decrypt(null, 0)).isNull();
        assertThat(encrypt(null, 1)).isNull();
        assertThat(decrypt(null, 1)).isNull();
    }

}
