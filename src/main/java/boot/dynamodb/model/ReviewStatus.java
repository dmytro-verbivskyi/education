package boot.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

@DynamoDBTypeConvertedEnum
public enum ReviewStatus {
    PENDING, CONFIRMED
}
