package boot.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.google.common.base.Objects;

@DynamoDBDocument
public class Owner {

    private String id;
    private String name;
    private String email;

    public String getId() {
        return id;
    }

    public Owner setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Owner setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Owner setEmail(String email) {
        this.email = email;
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
        Owner owner = (Owner) o;
        return Objects.equal(getId(), owner.getId())
                && Objects.equal(getName(), owner.getName())
                && Objects.equal(getEmail(), owner.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getName(), getEmail());
    }
}
