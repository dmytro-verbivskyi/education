package interview;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OverridingTest {

    @Test
    public void overridingVsHiding() throws Exception {
        A a = new A();
        assertThat(a.aMethod()).isEqualTo("A");
        assertThat(a.bField).isEqualTo("B");
        assertThat(a.cMethod()).isEqualTo("C");
        assertThat(a.dField).isEqualTo("D");

        a = new B();
        assertThat(a.aMethod()).isEqualTo("A");
        assertThat(a.bField).isEqualTo("B");
        assertThat(a.cMethod()).as("real overriding").isEqualTo("C1");
        assertThat(a.dField).as("taking by ref").isEqualTo("D");

        B b = new B();
        assertThat(b.aMethod()).isEqualTo("A1");
        assertThat(b.bField).isEqualTo("B1");
        assertThat(b.cMethod()).isEqualTo("C1");
        assertThat(b.dField).isEqualTo("D1");
    }

    private static class A {
        public static String aMethod() {
            return "A";
        }

        public static String bField = "B";

        public String cMethod() {
            return "C";
        }

        public String dField = "D";
    }

    private static class B extends A {
        public static String aMethod() {
            return "A1";
        }

        public static String bField = "B1";

        public String cMethod() {
            return "C1";
        }

        public String dField = "D1";
    }

}
