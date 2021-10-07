package minho.review.user.domain;

import lombok.Getter;
import lombok.Setter;
import minho.review.post.domain.Post;
import minho.review.common.utils.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Getter @Setter
public class User extends BaseEntity {
    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
