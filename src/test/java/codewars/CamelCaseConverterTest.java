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

    @Test
    public void testVeryLongConversion() {
        long heapSize = Runtime.getRuntime().totalMemory();
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        long heapFreeSize = Runtime.getRuntime().freeMemory();

        System.out.println(heapSize);
        System.out.println(heapMaxSize);
        System.out.println(heapFreeSize);

        String s = "Lorem-Ipsum-is-simply-dummy-text_of-the-printing-and-typesetting-industry._Lorem" +
                "-Ipsum-has-been-the-industry's-standard-dummy-text_ever-since-the-1500s,-when-an-unknown-printer-took" +
                "-a-galley_of-type-and-scrambled-it-to-make-a-type-specimen-book._It-has-survived-not-only-five" +
                "-centuries,-but-also-the-leap_into-electronic-typesetting,-remaining-essentially-unchanged._It-was" +
                "-popularised-in-the-1960s-with-the-release-of-Letraset_sheets-containing-Lorem-Ipsum-passages,-and" +
                "-more-recently-with_desktop-publishing-software-like-Aldus-PageMaker-including_versions-of-Lorem-Ipsum.";
        for (int i = 0; i < 5000; i++) {
            toCamelCase(s);
        }

        assertThat(toCamelCase(s))
                .isEqualTo("LoremIpsumIsSimplyDummyTextOfThePrintingAndTypesettingIndustry.LoremIpsumHasBeenThe" +
                        "Industry'sStandardDummyTextEverSinceThe1500s,WhenAnUnknownPrinterTookAGalleyOfTypeAndScrambled" +
                        "ItToMakeATypeSpecimenBook.ItHasSurvivedNotOnlyFiveCenturies,ButAlsoTheLeapIntoElectronic" +
                        "Typesetting,RemainingEssentiallyUnchanged.ItWasPopularisedInThe1960sWithTheReleaseOfLetraset" +
                        "SheetsContainingLoremIpsumPassages,AndMoreRecentlyWithDesktopPublishingSoftwareLikeAldusPage" +
                        "MakerIncludingVersionsOfLoremIpsum."
                );

        heapSize = Runtime.getRuntime().totalMemory();
        heapMaxSize = Runtime.getRuntime().maxMemory();
        heapFreeSize = Runtime.getRuntime().freeMemory();

        System.out.println(heapSize);
        System.out.println(heapMaxSize);
        System.out.println(heapFreeSize);
    }
}
