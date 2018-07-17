package boot.dynamodb.dao;

import boot.dynamodb.model.Review;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReviewCustomForDynamoQueriesImpl implements ReviewCustomForDynamoQueries {

    private static final Logger LOG = LogManager.getLogger(ReviewCustomForDynamoQueriesImpl.class);

    private final DynamoDBMapper mapper;

    @Autowired
    public ReviewCustomForDynamoQueriesImpl(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Review> findByReviewIdAndReviewOwnerId(String reviewId, String ownerId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":reviewId", new AttributeValue().withS(reviewId));
        eav.put(":ownerId", new AttributeValue().withS(ownerId));

        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("reviewId = :reviewId AND reviewOwner.id = :ownerId")
                .withExpressionAttributeValues(eav);

        PaginatedScanList<Review> result = mapper.scan(Review.class, scan);
        if (result.size() > 1) {
            LOG.warn("There are duplicates for pair(reviewId, ownerId), size: {}, ids: [{}]"
                    , result.size()
                    , result.stream().map(Review::getId).collect(Collectors.joining(", ")));
        }
        return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
    }

}
