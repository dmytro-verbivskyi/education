package boot.dynamodb.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface TableNameResolver {

    String value();

    class NoTableNameResolverException extends RuntimeException {
        NoTableNameResolverException(Class clazz) {
            super(String.format("There is no TableNameResolver annotation with %s.class", clazz.getSimpleName()));
        }
    }

    class NoPropertyValueTableNameResolverException extends RuntimeException {
        NoPropertyValueTableNameResolverException(String propertyName) {
            super(String.format("No property found '%s' to configure dynamo table", propertyName));
        }
    }
}
