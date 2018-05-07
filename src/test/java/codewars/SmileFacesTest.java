package codewars;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SmileFacesTest {

    @Test
    public void test1() {
        List<String> a = Arrays.asList(":)", ":D", ":-}", ":-()");
        assertEquals(2, SmileFaces.countSmileys(a));
    }

    @Test
    public void test2() {
        List<String> a = Arrays.asList(":)", "XD", ":0}", "x:-", "):-", "D:");
        assertEquals(1, SmileFaces.countSmileys(a));
    }

    @Test
    public void test4() {
        List<String> a = Arrays.asList(":)", ":D", "X-}", "xo)", ":X", ":-3", ":3");
        assertEquals(2, SmileFaces.countSmileys(a));
    }

    @Test
    public void test5() {
        List<String> a = Arrays.asList(
                "()D", ":)", "(~D", "'8P", "8)", "(D)", ":)", "8xd", ";dD", ";~D", "oD", ";~P", ";)", "8oD", ";)",
                ":DD", "8d", ";dD", "4)", "5)", ":Pp", "(d", ";od", "8x)", "4x", "2pdD", "ooX", ";D", "'o)", ":)",
                "5)", "-4P", "'D", "4X", "5~DX", ":8D", "ox", ":D", ";-)");
        assertEquals(9, SmileFaces.countSmileys(a));
    }
}
