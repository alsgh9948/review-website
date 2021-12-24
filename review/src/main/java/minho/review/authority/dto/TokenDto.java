package minho.review.authority.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TokenDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Request{
        private String accessToken;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class Repsonse{
        private String id;
        private String accessToken;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    public static class RepsonseAll{
        private String id;
        private String accessToken;
        private String refreshToken;
    }
}
