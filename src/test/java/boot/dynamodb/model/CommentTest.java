package boot.dynamodb.model;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {

    @Test
    public void coverEntity() throws Exception {
        Comment one = new Comment();
        one.setId("id");
        one.setUserId("userId");
        one.setComment("comment");
        one.setTimestamp("timestamp");
        one.setCollectionId("collectionId");
        one.setAssetId("assetId");

        Comment two = SerializationUtils.clone(one);
        Comment three = SerializationUtils.clone(one);
        three.setAssetId("another value");

        assertThat(one)
                .isEqualTo(one)
                .isEqualTo(two)
                .isNotEqualTo(three)
                .isNotEqualTo(null)
                .isNotEqualTo("string")
                .hasSameHashCodeAs(two)
                .hasToString("Comment{id='id', userId='userId', comment='comment', timestamp='timestamp'"
                        + ", collectionId='collectionId', assetId='assetId'}");
    }

}