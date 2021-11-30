package minho.review.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    USER("1", "일반유저"), ADMIN("2", "관리자");

    String code;
    String value;

    Role(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
