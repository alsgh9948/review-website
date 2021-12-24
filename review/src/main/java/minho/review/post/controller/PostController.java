package minho.review.post.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.common.validationgroup.CreateValidationGroup;
import minho.review.common.validationgroup.UpdateValidationGroup;
import minho.review.post.dto.PostDto;
import minho.review.post.dto.PostsDto;
import minho.review.post.sevice.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/{postId}")
    public ResponseEntity<Message> getPost(@PathVariable String postId){
        PostDto.Response response = postService.getPost(postId);

        Message message = new Message();
        message.setMessage("게시글 조회");
        message.setData(response);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Message> getAllPost(){
        PostsDto.Response response = postService.getAllPost();

        Message message = new Message();
        message.setMessage("전체 게시글 조회");
        message.setData(response);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> createPost(@RequestBody @Validated(CreateValidationGroup.class) PostDto.Request postRequestDto) {
        PostDto.Response response = postService.createPost(postRequestDto);

        Message message = new Message();
        message.setMessage("게시글 생성");
        message.setData(response);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{postId}", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> updatePost(@PathVariable String postId, @RequestBody @Validated(UpdateValidationGroup.class) PostDto.Request postRequestDto) {
        PostDto.Response response = postService.updatePost(postId,postRequestDto);

        Message message = new Message();
        message.setMessage("게시글 수정");
        message.setData(response);
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
