package minho.review.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FindDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findUsernameRequest{
        String email;
        String phone;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findUsernameResponse{
        String username;
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findPasswordRequest{
        String username;
        String email;
        String phone;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findPasswordResponse{
        String temporaryPassword;
    }
}
