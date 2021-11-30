package minho.review.authority;

import minho.review.common.utils.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorityController {

    @GetMapping("/test")
    public ResponseEntity<Message> authTest (){
        Message message = new Message();
        message.setMessage("data");
        message.setData("ok");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
