package assertj;

import com.google.common.base.Objects;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.util.Lists.newArrayList;

public class PojoAssertTest {

    private AssertedPerson jack = new AssertedPerson(42L, "Jack", "Sparrow");
    private AssertedPerson mary = new AssertedPerson(43L, "Mary", "Poppins");
    private AssertedPerson jose = new AssertedPerson(44L, "Jose", "Lopez");

    private AssertedCreditCard card1 = new AssertedCreditCard("1111", "11/42", 775);
    private AssertedCreditCard card2 = new AssertedCreditCard("2222", "11/42", 146);
    private AssertedCreditCard card3 = new AssertedCreditCard("3333", "10/33", 951);

    @Before
    public void setUp() throws Exception {
        jack.cardList = newArrayList(card1, card2);
        mary.cardList = newArrayList(card1, card3);
    }

    @Test
    public void pojo() throws Exception {
        assertThat(newArrayList(jack, mary))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .doesNotContainNull()
                .isSubsetOf(jack, mary, jose)
                .doesNotHaveDuplicates()
                .isSortedAccordingTo(Comparator.comparingLong(o -> o.id))
                .hasOnlyElementsOfType(AssertedPerson.class)
                .extracting("firstName", "lastName", "id")
                .containsExactly(
                        tuple("Jack", "Sparrow", 42L), tuple("Mary", "Poppins", 43L));

        assertThat(newArrayList(jack, mary))
                .flatExtracting("firstName", "lastName", "id")
                .containsExactly("Jack", "Sparrow", 42L, "Mary", "Poppins", 43L);

        assertThat(newArrayList(jack, mary))
                .flatExtracting("cardList")
                .extracting("numberString", "cvv2")
                .containsExactly(
                        tuple("1111", 775),
                        tuple("2222", 146),
                        tuple("1111", 775),
                        tuple("3333", 951))
        ;
    }

    @Test
    public void softAssert() throws Exception {
        try {
            SoftAssertions softly = new SoftAssertions();

            softly.assertThat(newArrayList(jack, mary, jose))
                    .hasSize(2)
                    .doesNotContainNull()
                    .extracting("firstName", "lastName", "id")
                    .containsExactly(
                            tuple("Jack", "Sparrow", 42L), tuple("Mary", "Poppins", 43L));
            softly.assertAll();
        } catch (AssertionError e) {
            System.out.println(e);
        }
    }

    class AssertedPerson {
        Long id;
        String firstName;
        String lastName;
        List<AssertedCreditCard> cardList;

        AssertedPerson(Long id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AssertedPerson that = (AssertedPerson) o;
            return Objects.equal(id, that.id)
                    && Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName)
                    && Objects.equal(cardList, that.cardList);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, firstName, lastName, cardList);
        }
    }

    class AssertedCreditCard {
        String numberString;
        String expireString;
        Integer cvv2;

        AssertedCreditCard(String numberString, String exireString, Integer cvv2) {
            this.numberString = numberString;
            this.expireString = exireString;
            this.cvv2 = cvv2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AssertedCreditCard that = (AssertedCreditCard) o;
            return Objects.equal(numberString, that.numberString)
                    && Objects.equal(expireString, that.expireString)
                    && Objects.equal(cvv2, that.cvv2);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(numberString, expireString, cvv2);
        }
    }
}


