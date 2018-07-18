package boot.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

@DynamoDBDocument
public class Asset implements Serializable {

    private Long assetId;
    private ApprovalStatus status;

    public Long getAssetId() {
        return assetId;
    }

    public Asset setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public Asset setStatus(ApprovalStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asset asset = (Asset) o;
        return Objects.equal(getAssetId(), asset.getAssetId())
                && getStatus() == asset.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAssetId(), getStatus());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("assetId", assetId)
                .add("status", status)
                .toString();
    }
}
