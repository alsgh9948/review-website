package minho.review.user.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.common.validationgroup.CreateValidationGroup;
import minho.review.common.validationgroup.UpdateValidationGroup;
import minho.review.post.domain.Post;
import minho.review.post.sevice.PostService;
import minho.review.user.domain.User;
import minho.review.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping(value = "/join", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> join (@RequestBody @Validated(CreateValidationGroup.class) User user){
        String userId = userService.join(user);
        Message message = new Message();
        message.setMessage("회원가입 성공");
        message.setData(userId);
        return new ResponseEntity<Message>(message,HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Message> getUser (@PathVariable String userId){
        User user = userService.findById(userId);

        Message message = new Message();
        message.setMessage("유저 정보 조회");
        message.setData(user);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/update", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> updateUser (@RequestBody @Validated(UpdateValidationGroup.class) User user){
        String userId = userService.updateUser(user);

        Message message = new Message();
        message.setMessage("유저 정보 수정");
        message.setData(userId);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/login", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> login (@RequestBody User user){
        Map<String, Object> loginInfo = userService.login(user.getUsername(),user.getPassword());

        Message message = new Message();
        message.setMessage("로그인 성공");
        message.setData(loginInfo);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/logout", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> logout (@RequestHeader (name="Authorization") String bearerToken, @RequestBody User user){
        userService.logout(bearerToken, user);

        Message message = new Message();
        message.setMessage("로그아웃 성공");
        message.setData("done.");
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/find_id", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> findMyId(@RequestBody User user){
        String FindResult = userService.findMyId(user);
        Message message = new Message();

        message.setMessage("아이디 찾기 성공");
        message.setData(FindResult);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/find_password", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> findMyPassword(@RequestBody User user){
        String temporaryPassword = userService.findMyPassword(user);
        Message message = new Message();

        message.setMessage("비밀번호 찾기 성공");
        message.setData("임시 비밀번호 : "+temporaryPassword);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/refresh_access_token", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> refreshAccessToken(@RequestHeader (name="Authorization") String bearerToken, @RequestBody User user){

        Map<String, String> jwt = userService.refreshAccessToken(bearerToken, user);

        Message message = new Message();
        message.setMessage("Access Token 갱신");
        message.setData(jwt);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @GetMapping(value = "/post/{userId}")
    public ResponseEntity<Message> getUserPost(@PathVariable String userId){
        List<Post> posts = postService.findByWriter(userId);

        Message message = new Message();
        message.setMessage("유저 게시글 조회");
        message.setData(posts);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
