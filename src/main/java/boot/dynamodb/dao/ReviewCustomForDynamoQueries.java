package boot.dynamodb.dao;

import boot.dynamodb.model.Review;

import java.util.Optional;

public interface ReviewCustomForDynamoQueries {

    Optional<Review> findByReviewIdAndReviewOwnerId(String reviewId, String ownerId);

}
