package minho.review.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import minho.review.common.utils.ErrorResponse;
import minho.review.common.enums.ErrorCode;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse("400",0,"111",e.getMessage());

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATER_USER);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<ErrorResponse> handleNotExistUserException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_USER);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
    }
}
