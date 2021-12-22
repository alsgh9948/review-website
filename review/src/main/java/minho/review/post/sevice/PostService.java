package minho.review.post.sevice;

import lombok.RequiredArgsConstructor;
import minho.review.post.domain.Post;
import minho.review.post.dto.CreatePostDto;
import minho.review.post.exception.DuplicatePostException;
import minho.review.post.exception.NotExistPostException;
import minho.review.post.repository.PostRepository;
import minho.review.user.domain.User;
import minho.review.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createPost(CreatePostDto createPostDto){
        validateDuplicatePostTitle(createPostDto);

        User writer = userRepository.findById(createPostDto.getUser_id()).orElseThrow(NotExistPostException::new);

        Post post = new Post();
        post.setTitle(createPostDto.getTitle());
        post.setContents(createPostDto.getContents());
        post.setUser(writer);

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public String updatePost(String postId, Post post){
        Post findPost = findById(postId);

//        validateDuplicatePostTitle(post);

        findPost.setTitle(post.getTitle());
        findPost.setContents(post.getContents());

        postRepository.save(findPost);
        return findPost.getId();
    }

    @Transactional
    public void updateViewCount(Post post){
        post.setViewCount(post.getViewCount()+1);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(String postId){
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        postRepository.deleteById(postId);
    }

    public List<Post> findByWriter(String id){
        return postRepository.findByUser_IdOrderByCreateDate(id);
    }

    public Post findById(String postId){
        return postRepository.findById(postId).orElseThrow(NotExistPostException::new);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public void validateDuplicatePostTitle(CreatePostDto post){
        Optional<Post> findPost = postRepository.findByTitle(post.getTitle());
        if (findPost.isPresent()){
            throw new DuplicatePostException();
        }
    }

}
