package minho.review.post.sevice;

import lombok.RequiredArgsConstructor;
import minho.review.post.domain.Post;
import minho.review.post.dto.PostDto;
import minho.review.post.dto.PostsDto;
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
    public PostDto.Response createPost(PostDto.Request postRequestDto){
//        validateDuplicatePostTitle(postRequestDto);

        User writer = userRepository.findById(postRequestDto.getUserId()).orElseThrow(NotExistPostException::new);

        Post post = postRequestDto.toEntity(writer);

        postRepository.save(post);
        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response updatePost(String postId, PostDto.Request postRequestDto){
        Post findPost = postRepository.findById(postId).orElseThrow(NotExistPostException::new);

        System.out.println(findPost.getId());
//        validateDuplicatePostTitle(findPost);

        if(postRequestDto.getTitle() != null) {
            findPost.setTitle(postRequestDto.getTitle());
        }

        if(postRequestDto.getContents() != null) {
            findPost.setContents(postRequestDto.getContents());
        }

        postRepository.save(findPost);
        return new PostDto.Response(findPost);
    }

    @Transactional
    public PostDto.Response getPost(String postId){
        postRepository.updateViewCount(postId);
        Post findPost = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        return new PostDto.Response(findPost);
    }

    @Transactional
    public void deletePost(String postId){
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        postRepository.deleteById(postId);
    }

    public List<Post> findByWriter(String id){
        return postRepository.findByUser_IdOrderByCreateDate(id);
    }

    public PostsDto.Response getAllPost(){
        return new PostsDto.Response(postRepository.findAll());
    }

    public void validateDuplicatePostTitle(PostDto.Request postReqeustDto){
        Optional<Post> findPost = postRepository.findByTitle(postReqeustDto.getTitle());
        if (findPost.isPresent()){
            throw new DuplicatePostException();
        }
    }

}
