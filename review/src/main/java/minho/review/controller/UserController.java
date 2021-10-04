package minho.review.controller;

import lombok.RequiredArgsConstructor;
import minho.review.domain.User;
import minho.review.service.UserService;
import minho.review.utils.Message;
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
    public ResponseEntity<String> join (@RequestBody User user){
        UUID join_uuid = userService.join(user);

        return new ResponseEntity<String>("done",HttpStatus.CREATED);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Message> getUserList (){
        List<User> userList = userService.findAll();

        Message message = new Message();
        message.setMessage("전체 유저 정보 조회");
        message.setData(userList);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }

    @PostMapping(value="/login", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> login (@RequestBody User user){
        boolean loginResult = userService.login(user.getId(),user.getPassword());
        Message message = new Message();

        if (loginResult){
            message.setMessage("로그인 성공");
            message.setData("Done");
            return new ResponseEntity<Message>(message,HttpStatus.OK);
        }
        else {
            message.setMessage("로그인 실패");
            message.setData("Not exist user");
            return new ResponseEntity<Message>(message,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/find_id", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> findMyId(@RequestBody String email, String phone){
        String FindResult = userService.findMyId(email,phone);
        Message message = new Message();

        if (!FindResult.isBlank()){
            message.setMessage("아이디 찾기 성공");
            message.setData(FindResult);
            return new ResponseEntity<Message>(message,HttpStatus.OK);
        }
        else {
            message.setMessage("아이디 찾기 실패");
            message.setData("Not exist user");
            return new ResponseEntity<Message>(message,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/find_password", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> findMyId(@RequestBody String id,String email, String phone){
        String FindResult = userService.findMyPassword(id,email,phone);
        Message message = new Message();

        if (!FindResult.isBlank()){
            message.setMessage("비밀번호 찾기 성공");
            message.setData(FindResult);
            return new ResponseEntity<Message>(message,HttpStatus.OK);
        }
        else {
            message.setMessage("비밀번호 찾기 실패");
            message.setData("Not exist user");
            return new ResponseEntity<Message>(message,HttpStatus.NOT_FOUND);
        }
    }
}
