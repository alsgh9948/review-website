package minho.review.user.dto;

import lombok.*;
import minho.review.user.domain.Role;
import minho.review.user.domain.User;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class UserDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter @Getter
    @ToString
    public static class Info{
        String id;
        String username;
        String email;
        String phone;
        Role role;
        LocalDateTime createDate;
        LocalDateTime updateDate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter @Getter
    public static class Request{
        String id;
        String username;
        String password;
        String email;
        String phone;
        Role role;

        public User toEntity(){
            return User.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .email(email)
                    .phone(phone)
                    .role(role)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter @Getter
    public static class Response{
        String id;
        String username;
        String email;
        String phone;
        Role role;
        LocalDateTime createDate;
        LocalDateTime updateDate;

        public Response(User info){
            this.id = info.getId();
            this.username = info.getUsername();
            this.email = info.getEmail();
            this.phone = info.getPhone();
            this.role = info.getRole();
            this.createDate = info.getCreateDate();
            this.updateDate = info.getUpdateDate();
        }
        public Response(User info, Role role){
            this.id = info.getId();
            this.username = info.getUsername();
            this.email = info.getEmail();
            this.phone = info.getPhone();
            this.createDate = info.getCreateDate();
            this.updateDate = info.getUpdateDate();

            if(role == null){
                this.role = Role.USER;
            }
            else{
                this.role = Stream.of(Role.values())
                        .filter(r -> r.getCode().equals(info.getRole().getCode()))
                        .findFirst()
                        .orElseThrow(IllegalArgumentException::new);
            }
        }
    }
}
