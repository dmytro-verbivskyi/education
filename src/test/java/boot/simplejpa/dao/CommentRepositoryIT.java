package boot.simplejpa.dao;

import boot.simplejpa.SimpleJpaApp;
import boot.simplejpa.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SimpleJpaApp.class})
public class CommentRepositoryIT {

    @Resource
    private CommentRepository commentRepository;

    @Before
    public void setUp() {
        commentRepository.deleteAll();
    }

    @Test
    public void findById() {
        Comment one = provideComment("u", "c", "t", "col", "a");
        commentRepository.save(one);

        String id = one.getId();
        Optional<Comment> commentOptional = commentRepository.findById(id);
        assertThat(commentOptional).isPresent().hasValue(one);
    }

    @Test
    public void findByUserId() {
        Comment one = provideComment("u", "c", "t", "col", "a");
        commentRepository.save(one);

        List<Comment> list = commentRepository.findByUserId("u");
        assertThat(list).hasSize(1).containsExactly(one);
    }

    @Test
    public void findByCollectionIdAndAssetIdNotNull() {
        commentRepository.saveAll(Arrays.asList(
            provideComment("u", "c1", "t", "col", null),
            provideComment("u", "c2", "t", "col", "a"),
            provideComment("u", "c3", "t", "col", "a"),
            provideComment("u", "c4", "t", "___", "a"),
            provideComment("u", "c5", "t", "___", "a")
        ));

        List<Comment> list = commentRepository.findByCollectionIdAndAssetIdNotNull("col");
        assertThat(list).hasSize(2)
                .extracting("comment").contains("c2", "c3");
    }

    @Test
    public void queryByExample() {
        commentRepository.saveAll(Arrays.asList(
                provideComment("u", "c1", "t", "col", null),
                provideComment("u", "c2", "t", "col", "a"),
                provideComment("u", "c3", "t", "col", "a"),
                provideComment("u", "c4", "t", "___", "a"),
                provideComment("u", "c5", "t", "___", "a")
        ));

        Comment example = provideComment(null, "c5", null, null, "a");
        List<Comment> list = commentRepository.findAll(Example.of(example));
        assertThat(list).hasSize(1)
                .extracting("comment", "collectionId").contains(tuple("c5", "___"));
    }

    private Comment provideComment(String user, String comment, String timestamp, String collection, String asset) {
        Comment c = new Comment();
        c.setUserId(user);
        c.setComment(comment);
        c.setTimestamp(timestamp);
        c.setCollectionId(collection);
        c.setAssetId(asset);
        return c;
    }
}