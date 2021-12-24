package minho.review.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    USER("1", "ROLE_USER"), ADMIN("2", "ROLE_ADMIN");

    String code;
    String value;

    Role(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
