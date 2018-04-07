package codewars;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CreditCardMaskTest {

    @Test
    public void emptyMask() {
        assertThat(Maskify.maskify("")).isEmpty();
    }

    @Test
    public void fourCharsMask() {
        assertThat(Maskify.maskify("1234")).isEqualTo("1234");
    }

    @Test
    public void skippyMask() {
        assertThat(Maskify.maskify("Skippy")).isEqualTo("##ippy");
    }

    @Test
    public void testSolution() {
        assertEquals(Maskify.maskify("4556364607935616"), "############5616");
        assertEquals(Maskify.maskify("64607935616"), "#######5616");

        // "What was the name of your first pet?"
        assertEquals(Maskify.maskify("Skippy"), "##ippy");
        assertEquals(Maskify.maskify("Nananananananananananananananana Batman!"), "####################################man!");
    }
}
