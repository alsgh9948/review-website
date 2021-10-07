package minho.review.post.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.post.domain.Post;
import minho.review.post.sevice.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Message> getPost(@PathVariable UUID uuid){
        Post post = postService.findOne(uuid);
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
    public ResponseEntity<Message> createPost(@RequestBody Post post) {
        UUID uuid = postService.createPost(post);

        Message message = new Message();
        message.setMessage("게시글 생성");
        message.setData(uuid);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{postUuid}", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> updatePost(@PathVariable UUID postUuid, @RequestBody Post post) {
        UUID uuid = postService.updatePost(postUuid,post);

        Message message = new Message();
        message.setMessage("게시글 수정");
        message.setData(uuid);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{postUuid}", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> deletePost(@PathVariable UUID postUuid) {
        postService.deletePost(postUuid);

        Message message = new Message();
        message.setMessage("게시글 삭제");
        message.setData("Done.");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
