package minho.review.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minho.review.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UsersDto {

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info{
        List<User> userList;
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Response{
        List<UserDto.Response> userList;

        public Response(List<User> userList){
            this.userList = userList.stream()
                    .map(UserDto.Response::new)
                    .collect(Collectors.toList());
        }

    }
}
