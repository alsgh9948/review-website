package minho.review.user.dto;

import lombok.*;
import minho.review.user.domain.Role;
import minho.review.user.domain.RoleConverter;
import minho.review.user.domain.User;

import javax.persistence.Convert;

public class JoinDto {

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        private String username;
        private String password;
        private String email;
        private String phone;

        @Convert(converter = RoleConverter.class)
        private Role role;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .role(role)
                    .build();
        }

    }

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private User info;

        public Response(User info, Role role){
            this.info = info;
            if(role == null){
                this.info.setRole(Role.USER);
            }
            else{
                this.info.setRole(Role.ADMIN);
            }
        }
    }

}
