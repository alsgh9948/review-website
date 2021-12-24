package minho.review.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minho.review.post.domain.Post;
import minho.review.user.domain.User;

public class PostDto {

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info{
        private String id;
        private String title;
        private String contents;
        private int viewCount;
        private User user;

        public Info(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.contents = post.getContents();
            this.viewCount = post.getViewCount();
            this.user = post.getUser();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String id;
        private String title;
        private String contents;
        private String userId;

        public Post toEntity(User writer){
            return Post.builder()
                    .id(id)
                    .title(title)
                    .contents(contents)
                    .user(writer)
                    .build();
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String id;
        private String title;
        private String contents;
        private int viewCount;
        private String userId;

        public Response(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.contents = post.getContents();
            this.viewCount = post.getViewCount();
            this.userId = post.getUser().getId();
        }
    }
}
