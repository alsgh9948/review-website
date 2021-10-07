package minho.review.post.exception;

public class NotExistPostException extends RuntimeException{
    public NotExistPostException() {
    }

    public NotExistPostException(String message) {
        super(message);
    }

    public NotExistPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistPostException(Throwable cause) {
        super(cause);
    }

    public NotExistPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
