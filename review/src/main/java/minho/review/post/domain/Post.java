package minho.review.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import minho.review.user.domain.User;
import minho.review.common.utils.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="post")
@Getter @Setter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "uuid")
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String contents;

    @ColumnDefault("1")
    private int viewCount;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", viewCount=" + viewCount +
                ", user=" + user +
                '}';
    }
}
