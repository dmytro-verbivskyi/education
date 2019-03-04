package array;

import org.junit.Test;

import java.util.Arrays;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

public class AddToArrayTest {

    @Test
    public void arrayMightBeNull() {
        assertThat(addIntoArray(null, "hello")).containsExactly("hello");
    }

    @Test
    public void arrayMightContainsValues() {
        String[] array = new String[]{"dear", "world"};
        assertThat(addIntoArray(array, "hello")).containsExactly("dear", "world", "hello");
    }

    @Test
    public void arrayMightBeNullAndValueAlsoMightBeNull() {
        assertThat(addIntoArray(null, null)).isEmpty();
    }

    @Test
    public void arrayIsNotEmptyButValueIsNull() {
        String[] array = new String[]{"dear", "world"};
        assertThat(addIntoArray(array, null)).containsExactly("dear", "world");
    }

    @Test
    public void arrayIsEmptyValueIsNull() {
        String[] array = new String[]{};
        assertThat(addIntoArray(array, null)).isEmpty();
    }

    @Test
    public void arrayIsEmptyValueIsNotNull() {
        String[] array = new String[]{};
        assertThat(addIntoArray(array, "hello")).containsExactly("hello");
    }

    private String[] addIntoArray(String[] array, String str) {
        if (isNull(array)) {
            if (isNull(str)) {
                return new String[]{};
            }
            return new String[]{str};
        }
        if (isNull(str)) {
            return array;
        }
        String[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = str;
        return newArray;
    }
}
