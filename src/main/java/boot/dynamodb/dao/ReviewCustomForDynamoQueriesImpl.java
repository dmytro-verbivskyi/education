package boot.dynamodb.dao;

import boot.dynamodb.model.Review;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewCustomForDynamoQueriesImpl implements ReviewCustomForDynamoQueries {

    private final DynamoDBMapper mapper;

    @Autowired
    public ReviewCustomForDynamoQueriesImpl(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Review findByReviewIdAndOwnerId(String reviewId, String ownerId) {
//        mapper.(user);
        int a = 343;

        return null;
    }
}
