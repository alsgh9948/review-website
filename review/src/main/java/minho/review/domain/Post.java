package minho.review.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Post {
    @Id
    private UUID post_uuid;

    private String title;

    private String contents;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_uuid")
    private User user;
}
