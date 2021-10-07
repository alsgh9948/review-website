package minho.review.post.sevice;

import minho.review.post.domain.Post;
import minho.review.post.exception.DuplicatePostException;
import minho.review.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostRepository postRepository;
    @Autowired PostService postService;

    @Test
    void createPost() {
        Post post = tempPostCreate();

        UUID uuid = postService.createPost(post);

        Assertions.assertEquals(uuid,post.getUuid());
    }

    @Test
    void updatePost() {
        Post post = tempPostCreate();
        UUID uuid = postService.createPost(post);

        String updateTitle = "update title";
        post.setTitle(updateTitle);
        postService.updatePost(uuid,post);

        Assertions.assertEquals(updateTitle, postRepository.findOne(uuid).getTitle());
    }

    @Test
    void deletePost() {
        Post post = tempPostCreate();
        UUID uuid = postService.createPost(post);

        postService.deletePost(uuid);

        Assertions.assertNull(postService.findOne(uuid));
    }

    @Test
    void findOne() {
        Post post = tempPostCreate();
        UUID uuid = postService.createPost(post);

        Assertions.assertEquals(uuid,postService.findOne(uuid).getUuid());
    }

    @Test
    void validateDuplicatePostTitle() {
        Post post1 = tempPostCreate();
        UUID uuid = postService.createPost(post1);

        Post post2 = tempPostCreate();

        DuplicatePostException e = assertThrows(DuplicatePostException.class, () -> {
            UUID uuid2 = postService.createPost(post2);
        });
    }

    Post tempPostCreate(){
        Post post = new Post();
        post.setTitle("post1");
        post.setContents("contents");

        return post;
    }
}