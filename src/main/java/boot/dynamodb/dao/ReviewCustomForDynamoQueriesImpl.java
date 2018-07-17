package boot.dynamodb.dao;

import boot.dynamodb.model.Asset;
import boot.dynamodb.model.Review;
import boot.dynamodb.model.ReviewStatus;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class ReviewCustomForDynamoQueriesImpl implements ReviewCustomForDynamoQueries {

    private static final Logger LOG = LogManager.getLogger(ReviewCustomForDynamoQueriesImpl.class);

    private final DynamoDBMapper mapper;

    @Autowired
    public ReviewCustomForDynamoQueriesImpl(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Review> findByReviewIdAndReviewOwnerId(String reviewId, String ownerId) {
        PaginatedScanList<Review> result = getByReviewIdAndOwnerId(reviewId, ownerId);
        if (result.size() > 1) {
            LOG.warn("There are duplicates for pair(reviewId, ownerId), size: {}, ids: [{}]"
                    , result.size()
                    , result.stream().map(Review::getId).collect(Collectors.joining(", ")));
        }
        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public int updateReviewStatus(String reviewId, String ownerId, ReviewStatus status) {
        checkArgument(nonNull(status), "reviewStatus cannot be null");

        PaginatedScanList<Review> result = getByReviewIdAndOwnerId(reviewId, ownerId);
        if (result.isEmpty()) {
            return 0;
        }
        for (Review review : result) {
            review.setStatus(status);
        }
        List<DynamoDBMapper.FailedBatch> failed = mapper.batchSave(result);
        return result.size() - failed.size();
    }

    @Override
    public int updateReviewAppovalStatus(String reviewId, String ownerId, Asset assetApprovals) {
        checkArgument(nonNull(assetApprovals), "assetApprovals cannot be null");
        checkArgument(nonNull(assetApprovals.getAssetId()), "assetId cannot be null");
        checkArgument(nonNull(assetApprovals.getStatus()), "approvalStatus cannot be null");

        PaginatedScanList<Review> result = getByReviewIdAndOwnerId(reviewId, ownerId);
        if (result.isEmpty()) {
            return 0;
        }

        boolean noUpdateNeeded = true;

        for (Review review : result) {
            for (Asset asset : review.getAssets()) {
                if (assetApprovals.getAssetId().equals(asset.getAssetId())) {
                    asset.setStatus(assetApprovals.getStatus());
                    noUpdateNeeded = false;
                }
            }
        }
        if (noUpdateNeeded) {
            return 0;
        }
        List<DynamoDBMapper.FailedBatch> failed = mapper.batchSave(result);
        return result.size() - failed.size();
    }

    private PaginatedScanList<Review> getByReviewIdAndOwnerId(String reviewId, String ownerId) {
        checkArgument(isNotBlank(reviewId), "reviewId cannot be null or blank");
        checkArgument(isNotBlank(ownerId), "ownerId cannot be null or blank");

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":reviewId", new AttributeValue().withS(reviewId));
        eav.put(":ownerId", new AttributeValue().withS(ownerId));

        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("reviewId = :reviewId AND reviewOwner.id = :ownerId")
                .withExpressionAttributeValues(eav);
        return mapper.scan(Review.class, scan);
    }

}
