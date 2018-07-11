package boot.dynamodb.util;

import boot.dynamodb.util.TableNameResolver.NoPropertyValueTableNameResolverException;
import boot.dynamodb.util.TableNameResolver.NoTableNameResolverException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

@Component
public class DynamicTableNameResolver implements DynamoDBMapperConfig.TableNameResolver {

    private final Environment env;

    private Map<Class, String> entityToTableName = Maps.newHashMap();

    @Autowired
    public DynamicTableNameResolver(Environment env) {
        this.env = env;
    }

    @Override
    public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
        String actualTableName = entityToTableName.get(clazz);
        if (actualTableName != null) {
            return actualTableName;
        }
        return tryGetFromAnnotation(clazz);
    }

    private String tryGetFromAnnotation(Class<?> clazz) {
        TableNameResolver tableNameResolver = clazz.getAnnotation(TableNameResolver.class);
        if (tableNameResolver == null) {
            throw new NoTableNameResolverException(clazz);
        }

        String actualTableName = env.getProperty(tableNameResolver.value());
        if (isNullOrEmpty(actualTableName)) {
            throw new NoPropertyValueTableNameResolverException(tableNameResolver.value());
        }
        entityToTableName.put(clazz, actualTableName);
        return actualTableName;
    }

}
