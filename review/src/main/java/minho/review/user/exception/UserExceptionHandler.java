package minho.review.user.exception;

import minho.review.user.enums.ErrorCode;
import minho.review.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> NotFoundUserInfomationException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATER_USER);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<ErrorResponse> NotExistUserException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_USER);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
    }
}
