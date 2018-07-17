package boot.dynamodb.model;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTest {

    @Test
    public void coverEntity() throws Exception {
        Owner one = new Owner()
                .setId("55L")
                .setName("name")
                .setEmail("email");

        Owner two = SerializationUtils.clone(one);
        Owner three = SerializationUtils.clone(one);
        three.setName("yo");

        assertThat(one)
                .isEqualTo(one)
                .isEqualTo(two)
                .isNotEqualTo(three)
                .isNotEqualTo(null)
                .isNotEqualTo("string")
                .hasSameHashCodeAs(two)
                .hasToString("Owner{id='55L', name='name', email='email'}");
    }
}