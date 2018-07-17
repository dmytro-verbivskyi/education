package boot.dynamodb.dao;

import boot.dynamodb.model.Review;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ReviewRepository extends CrudRepository<Review, String>, ReviewCustomForDynamoQueries {

    List<Review> findByReviewId(String reviewId);

    List<Review> findAllByReviewIdIn(List<String> reviewIds);

}
