package minho.review.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import minho.review.common.utils.BaseEntity;
import minho.review.common.validationgroup.CreateValidationGroup;
import minho.review.post.domain.Post;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="user")
@Getter @Setter
public class User extends BaseEntity {

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
