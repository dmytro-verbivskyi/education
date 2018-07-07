package boot.dynamodb.config;

import boot.dynamodb.model.Comment;
import boot.dynamodb.model.User;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Component
public class DynamicTableNameResolver implements DynamoDBMapperConfig.TableNameResolver {

    @Value("${dynamodb.table.name.comment}")
    private String commentTableName;

    @Value("${dynamodb.table.name.user}")
    private String userTableName;

    private Map<Class, String> entityToTableName;

    @Override
    public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
        return entityToTableName.get(clazz);
    }

    @PostConstruct
    public void postConstruct() {
        entityToTableName = ImmutableMap.<Class, String>builder()
                .put(Comment.class, requireNonNull(commentTableName))
                .put(User.class, requireNonNull(userTableName))
                .build();
    }

}
