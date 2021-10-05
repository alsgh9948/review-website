package minho.review.user.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Post {
    @Id @GeneratedValue
    private UUID post_uuid;

    @NonNull
    private String title;

    @NonNull
    private String contents;

    @NonNull
    private LocalDateTime createTime;

    @NonNull
    private LocalDateTime updateTime;

    @ColumnDefault("0")
    private int viewCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_uuid")
    private User user;
}
