package minho.review.user.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.user.domain.User;
import minho.review.user.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> join (@RequestBody User user){
        UUID join_uuid = userService.join(user);
        Message message = new Message();
        message.setMessage("회원가입 성공");
        message.setData(join_uuid);
        return new ResponseEntity<Message>(message,HttpStatus.CREATED);
    }

    @GetMapping(value = "/logTest")
    public ResponseEntity<Message> getLog (@RequestParam int[] data){
        Message message = new Message();
        message.setMessage("data");
        message.setData(data);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Message> getUser (@PathVariable UUID uuid){
        User user = userService.findOne(uuid);

        Message message = new Message();
        message.setMessage("유저 정보 조회");
        message.setData(user);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Message> getUserList (){
        List<User> userList = userService.findAll();
        System.out.println(userList.toString());
        Message message = new Message();
        message.setMessage("전체 유저 정보 조회");
        message.setData(userList);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/login", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> login (@RequestBody User user){
        UUID user_uuid = userService.login(user.getId(),user.getPassword());

        Message message = new Message();
        message.setMessage("로그인 성공");
        message.setData("Done");
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
        String FindResult = userService.findMyPassword(user);
        Message message = new Message();

        message.setMessage("비밀번호 찾기 성공");
        message.setData(FindResult);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }
}
