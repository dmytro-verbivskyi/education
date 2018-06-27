package boot.dynamodb.dao;

import boot.dynamodb.model.Comment;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CommentRepository extends CrudRepository<Comment, String> {

    /*
        ArrayList<Comment> findByCollectionIdAndAssetIdNull(String collectionId);

        ArrayList<Comment> findByCollectionIdAndAssetIdNullAndUserId(String collectionId, String userId);

        ArrayList<Comment> findByCollectionIdNullAndAssetId(String assetId);

        ArrayList<Comment> findByCollectionIdNullAndAssetIdAndUserId(String assetId, String userId);

        ArrayList<Comment> findByCollectionIdAndAssetId(String collectionId, String assetId);

        ArrayList<Comment> findByCollectionIdAndAssetIdAndUserId(String collectionId, String assetId, String userId);

     */

    List<Comment> findByCollectionIdAndAssetIdNotNull(String collectionId);

    List<Comment> findByUserId(String userId);

    // Comment findById(String id);  optional)

}