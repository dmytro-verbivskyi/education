package boot.simplejpa.dao;

import boot.simplejpa.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends
        JpaRepository<Comment, String>
{

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