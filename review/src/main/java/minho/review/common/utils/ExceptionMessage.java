package minho.review.common.utils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionMessage {

    private String message;
    private Object data;

    public ExceptionMessage(){}
}
