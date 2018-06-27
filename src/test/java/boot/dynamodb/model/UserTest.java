package boot.dynamodb.model;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void coverEntity() throws Exception {
        User one = new User();
        one.setId("id");
        one.setFirstName("firstName");
        one.setLastName("lastName");

        User two = SerializationUtils.clone(one);
        User three = SerializationUtils.clone(one);
        three.setLastName("another value");

        assertThat(one)
                .isEqualTo(one)
                .isEqualTo(two)
                .isNotEqualTo(three)
                .isNotEqualTo(null)
                .hasSameHashCodeAs(two)
                .hasToString("User{id='id', firstName='firstName', lastName='lastName'}");
    }
}