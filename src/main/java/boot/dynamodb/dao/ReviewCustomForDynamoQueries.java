package boot.dynamodb.dao;

import boot.dynamodb.model.Asset;
import boot.dynamodb.model.Review;
import boot.dynamodb.model.ReviewStatus;

import java.util.Optional;

public interface ReviewCustomForDynamoQueries {

    Optional<Review> findByReviewIdAndReviewOwnerId(String reviewId, String ownerId);

    int updateReviewStatus(String reviewId, String approverId, ReviewStatus status);

    int updateReviewAppovalStatus(String reviewId, String approverId, Asset assetApprovals);
}
