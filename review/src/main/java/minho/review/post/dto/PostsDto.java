package minho.review.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minho.review.post.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostsDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class Response{
        List<PostDto.Response> postList;

        public Response(List<Post> postList) {
            this.postList = postList.stream()
                    .map(PostDto.Response::new)
                    .collect(Collectors.toList());
        }
    }
}
