package minho.review.post.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.post.domain.Post;
import minho.review.post.dto.CreatePostDto;
import minho.review.post.sevice.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<Message> getPost(@PathVariable String postId){
        Post post = postService.findById(postId);
        postService.updateViewCount(post);

        Message message = new Message();
        message.setMessage("게시글 조회");
        message.setData(post);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Message> getPostList(){
        List<Post> postList = postService.findAll();

        Message message = new Message();
        message.setMessage("전체 게시글 조회");
        message.setData(postList);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> createPost(@RequestBody CreatePostDto createPostDto) {
        String postId = postService.createPost(createPostDto);

        Message message = new Message();
        message.setMessage("게시글 생성");
        message.setData(postId);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{postId}", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> updatePost(@PathVariable String postId, @RequestBody Post post) {
        String updatePostId = postService.updatePost(postId,post);

        Message message = new Message();
        message.setMessage("게시글 수정");
        message.setData(updatePostId);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{postId}", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> deletePost(@PathVariable String postId) {
        postService.deletePost(postId);

        Message message = new Message();
        message.setMessage("게시글 삭제");
        message.setData("Done.");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
