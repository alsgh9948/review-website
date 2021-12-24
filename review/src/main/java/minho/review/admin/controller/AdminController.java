package minho.review.admin.controller;

import lombok.RequiredArgsConstructor;
import minho.review.common.utils.Message;
import minho.review.common.validationgroup.UpdateValidationGroup;
import minho.review.user.dto.UserDto;
import minho.review.user.dto.UsersDto;
import minho.review.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping(value = "/user/list")
    public ResponseEntity<Message> getUserList (){
        UsersDto.Response response = userService.getAllUser();
        Message message = new Message();
        message.setMessage("전체 유저 정보 조회");
        message.setData(response);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @PostMapping(value="/user/update", produces = "application/json; charset=utf8")
    public ResponseEntity<Message> updateUser (@RequestBody @Validated(UpdateValidationGroup.class) UserDto.Request user){
        UserDto.Response response = userService.updateUser(user);

        Message message = new Message();
        message.setMessage("유저 정보 수정");
        message.setData(response);
        return new ResponseEntity<Message>(message,HttpStatus.OK);
    }
}
