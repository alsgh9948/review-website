package minho.review.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    DUPLICATER_USER(400,"1","Duplicate Information"),
    NOT_EXIST_USER(404,"2","Not Exist User Information"),

    DUPLICATER_POST(400,"10","Duplicate Title"),
    NOT_EXIST_POST(404,"11","Not Exist Post");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
