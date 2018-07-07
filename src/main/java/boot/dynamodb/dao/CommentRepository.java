package boot.dynamodb.dao;

import boot.dynamodb.model.Comment;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CommentRepository extends CrudRepository<Comment, String> {

    List<Comment> findByCollectionIdAndAssetIdNotNull(String collectionId);

    List<Comment> findByUserId(String userId);

}