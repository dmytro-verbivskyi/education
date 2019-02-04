package interview;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OverridingTest {

    // Everything is taken by ref... only instance method can be overridden enabling polymorphism.
    @Test
    public void overridingVsHiding() throws Exception {
        A a = new A();
        assertThat(a.staticMethodA()).isEqualTo("A");
        assertThat(a.staticFieldB).isEqualTo("B");
        assertThat(a.instanceMethodC()).isEqualTo("C");
        assertThat(a.instanceFieldD).isEqualTo("D");

        a = new B();
        assertThat(a.staticMethodA()).isEqualTo("A");
        assertThat(a.staticFieldB).isEqualTo("B");
        assertThat(a.instanceMethodC()).as("actual overriding").isEqualTo("C1");
        assertThat(a.instanceFieldD).isEqualTo("D");

        B b = new B();
        assertThat(b.staticMethodA()).isEqualTo("A1");
        assertThat(b.staticFieldB).isEqualTo("B1");
        assertThat(b.instanceMethodC()).isEqualTo("C1");
        assertThat(b.instanceFieldD).isEqualTo("D1");
    }

    private static class A {
        static String staticMethodA() {
            return "A";
        }

        static String staticFieldB = "B";

        String instanceMethodC() {
            return "C";
        }

        String instanceFieldD = "D";
    }

    private static class B extends A {
        static String staticMethodA() {
            return "A1";
        }

        static String staticFieldB = "B1";

        String instanceMethodC() {
            return "C1";
        }

        String instanceFieldD = "D1";
    }

}
