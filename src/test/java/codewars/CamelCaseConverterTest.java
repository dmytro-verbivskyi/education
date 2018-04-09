package codewars;

import org.junit.Test;

import static codewars.CamelCaseConverter.toCamelCase;
import static org.assertj.core.api.Assertions.assertThat;

public class CamelCaseConverterTest {

    @Test
    public void nullWord() {
        assertThat(toCamelCase(null)).isEmpty();
    }

    @Test
    public void emptyWord() {
        assertThat(toCamelCase("")).isEmpty();
    }

    @Test
    public void testSingleWord() {
        assertThat(toCamelCase("hello")).isEqualTo("hello");
    }

    @Test
    public void testSingleCapitalWord() {
        assertThat(toCamelCase("Hello")).isEqualTo("Hello");
    }

    @Test
    public void testSingleLetterComposition() {
        assertThat(toCamelCase("h-i")).isEqualTo("hI");
    }

    @Test
    public void testSomeUnderscoreLowerStart() {
        assertThat(toCamelCase("the_Stealth_Warrior")).isEqualTo("theStealthWarrior");
    }

    @Test
    public void testSomeDashLowerStart() {
        assertThat(toCamelCase("the-Stealth-Warrior")).isEqualTo("theStealthWarrior");
    }

    @Test
    public void testSomeUnderscoreCapitalStart() {
        assertThat(toCamelCase("The_Stealth_Warrior")).isEqualTo("TheStealthWarrior");
    }

    @Test
    public void testSomeDashCapitalStart() {
        assertThat(toCamelCase("The-Stealth-Warrior")).isEqualTo("TheStealthWarrior");
    }

    @Test
    public void testMixingDelimiters() {
        assertThat(toCamelCase("h-i_p_ho-p")).isEqualTo("hIPHoP");
    }

    @Test
    public void testRepeatingDashes() {
        assertThat(toCamelCase("h--i")).isEqualTo("hI");
        assertThat(toCamelCase("h-----i")).isEqualTo("hI");
    }

    @Test
    public void testRepeatingUnderscores() {
        assertThat(toCamelCase("h__i")).isEqualTo("hI");
        assertThat(toCamelCase("h_____i")).isEqualTo("hI");
    }

    @Test
    public void testRepeatingMixingDashes() {
        assertThat(toCamelCase("h-__--__i")).isEqualTo("hI");
    }
}
