package boot.dynamodb.model;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssetTest {

    @Test
    public void coverEntity() throws Exception {
        Asset one = new Asset()
                .setAssetId(55L)
                .setStatus(ApprovalStatus.REJECTED);

        Asset two = SerializationUtils.clone(one);
        Asset three = SerializationUtils.clone(one);
        three.setStatus(ApprovalStatus.PENDING);

        assertThat(one)
                .isEqualTo(one)
                .isEqualTo(two)
                .isNotEqualTo(three)
                .isNotEqualTo(null)
                .isNotEqualTo("string")
                .hasSameHashCodeAs(two)
                .hasToString("Asset{assetId=55, status=REJECTED}");
    }
}