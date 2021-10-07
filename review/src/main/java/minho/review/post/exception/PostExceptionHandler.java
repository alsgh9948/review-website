package minho.review.post.exception;

import minho.review.common.enums.ErrorCode;
import minho.review.common.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(DuplicatePostException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePostException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATER_POST);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistPostException.class)
    public ResponseEntity<ErrorResponse> handleNotExistPostExceptionException(final RuntimeException e){
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_POST);

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
    }

}
