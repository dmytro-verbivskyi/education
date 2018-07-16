package boot.dynamodb.dao;

import boot.dynamodb.model.Review;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ReviewRepository extends CrudRepository<Review, String> {

    List<Review> findByReviewId(String reviewId);

    Review findByReviewIdAndOwnerId(String reviewId, String id);

//    List<Review> findAllByReviewIdIn(List<String> reviewId);
}
