package boot.simplejpa.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import static boot.simplejpa.model.Comment.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Comment implements Serializable {

    public static final String TABLE_NAME = "comment";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String userId;
    private String comment;
    private String timestamp;
    private String collectionId;
    private String assetId;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public String getAssetId() {
        return assetId;
    }

    @Override
    public String toString() {
        return "Comment{"
                + "id='" + id + '\''
                + ", userId='" + userId + '\''
                + ", comment='" + comment + '\''
                + ", timestamp='" + timestamp + '\''
                + ", collectionId='" + collectionId + '\''
                + ", assetId='" + assetId + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment1 = (Comment) o;
        return Objects.equals(getId(), comment1.getId())
                && Objects.equals(getUserId(), comment1.getUserId())
                && Objects.equals(getComment(), comment1.getComment())
                && Objects.equals(getTimestamp(), comment1.getTimestamp())
                && Objects.equals(getCollectionId(), comment1.getCollectionId())
                && Objects.equals(getAssetId(), comment1.getAssetId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getComment(), getTimestamp(), getCollectionId(), getAssetId());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
