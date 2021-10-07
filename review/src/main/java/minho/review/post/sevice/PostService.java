package minho.review.post.sevice;

import lombok.RequiredArgsConstructor;
import minho.review.post.domain.Post;
import minho.review.post.exception.DuplicatePostException;
import minho.review.post.exception.NotExistPostException;
import minho.review.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public UUID createPost(Post post){
        validateDuplicatePostTitle(post);
        postRepository.save(post);
        return post.getUuid();
    }

    @Transactional
    public UUID updatePost(UUID uuid, Post post){
        Post findPost = findOne(uuid);

        validateDuplicatePostTitle(post);

        findPost.setTitle(post.getTitle());
        findPost.setContents(post.getContents());

        postRepository.save(findPost);
        return findPost.getUuid();
    }

    @Transactional
    public void updateViewCount(Post post){
        post.setViewCount(post.getViewCount()+1);
        System.out.println(post.getViewCount());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(UUID uuid){
        Post post = postRepository.findOne(uuid);

        if (post == null){
            throw new NotExistPostException();
        }
        else{
            postRepository.delete(uuid);
        }
    }
    public Post findOne(UUID uuid){
        Post post = postRepository.findOne(uuid);
        if (post == null) {
            throw new NotExistPostException();
        }
        else {
            return postRepository.findOne(uuid);
        }
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public void validateDuplicatePostTitle(Post post){
        Optional<Post> findPost = postRepository.findTitle(post.getTitle());
        if (findPost.isPresent()){
            System.out.println(findPost.get().getTitle());
            throw new DuplicatePostException();
        }
    }
}
