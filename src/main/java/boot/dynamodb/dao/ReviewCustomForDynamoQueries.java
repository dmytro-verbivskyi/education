package boot.dynamodb.dao;

import boot.dynamodb.model.Review;

public interface ReviewCustomForDynamoQueries {

    Review findByReviewIdAndOwnerId(String reviewId, String ownerId);

}
