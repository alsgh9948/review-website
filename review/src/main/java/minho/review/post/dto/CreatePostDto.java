package minho.review.post.dto;

import lombok.Data;

@Data
public class CreatePostDto {
    private String title;
    private String contents;
    private String user_id;
}
