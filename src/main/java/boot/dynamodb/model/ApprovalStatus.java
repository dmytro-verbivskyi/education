package boot.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

@DynamoDBTypeConvertedEnum
public enum ApprovalStatus {
    APPROVED,
    REJECTED,
    PENDING
}