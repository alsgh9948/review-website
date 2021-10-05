package minho.review.user.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter @Setter
public class User {
    @Id @GeneratedValue
    private UUID user_uuid;

    @NonNull
    private String id;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @NonNull
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
