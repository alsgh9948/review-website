package minho.review.authority.exception;

import minho.review.common.enums.ErrorCode;
import minho.review.common.utils.ErrorResponse;
import minho.review.common.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorityExceptionHandlerFilter extends OncePerRequestFilter {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> tokenCommonException(final RuntimeException e) {
//        ErrorResponse response = new ErrorResponse("400", 0, "111", e.getMessage());
//        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<ErrorResponse> invalidTokenException(final RuntimeException e) {
//        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
//        return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(ExpiredTokenException.class)
//    public ResponseEntity<ErrorResponse> expiredTokenException(final RuntimeException e){
//        ErrorResponse response = new ErrorResponse(ErrorCode.EXPIRED_TOKEN);
//        return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (InvalidTokenException ex){
            setErrorResponse(new ErrorResponse(ErrorCode.INVALID_TOKEN),response);
        }
        catch (ExpiredTokenException ex){
            setErrorResponse(new ErrorResponse(ErrorCode.EXPIRED_TOKEN),response);
        }
    }

    public void setErrorResponse(ErrorResponse errorResponse, HttpServletResponse response){
        try {
            response.setStatus(errorResponse.getStatus());
            response.getWriter().write(new Utils().ObjectToJson(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}