package interview;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OverridingTest {

    @Test
    public void overridingAndShadowing() throws Exception {
        A a = new A();
        assertThat(a.a()).isEqualTo("A");
        assertThat(a.b).isEqualTo("B");
        assertThat(a.c()).isEqualTo("C");
        assertThat(a.d).isEqualTo("D");

        a = new B();
        assertThat(a.a()).isEqualTo("A");
        assertThat(a.b).isEqualTo("B");
        assertThat(a.c()).as("real overriding").isEqualTo("C1");
        assertThat(a.d).as("taking by ref").isEqualTo("D");

        B b = new B();
        assertThat(b.a()).isEqualTo("A1");
        assertThat(b.b).isEqualTo("B1");
        assertThat(b.c()).isEqualTo("C1");
        assertThat(b.d).isEqualTo("D1");
    }

    private static class A {
        public static String a() {
            return "A";
        }

        public static String b = "B";

        public String c() {
            return "C";
        }

        public String d = "D";
    }

    private static class B extends A {
        public static String a() {
            return "A1";
        }

        public static String b = "B1";

        public String c() {
            return "C1";
        }

        public String d = "D1";
    }

}
