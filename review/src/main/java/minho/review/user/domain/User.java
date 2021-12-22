package minho.review.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import minho.review.common.utils.BaseEntity;
import minho.review.common.validationgroup.CreateValidationGroup;
import minho.review.post.domain.Post;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="user")
@Getter @Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "uuid")
    private String id;

    @NotNull(groups=CreateValidationGroup.class)
    private String username;

    @NotNull(groups=CreateValidationGroup.class)
    private String password;

    @NotNull(groups=CreateValidationGroup.class)
    private String email;

    @NotNull(groups=CreateValidationGroup.class)
    private String phone;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @Convert(converter = RoleConverter.class)
    private Role role;

}
