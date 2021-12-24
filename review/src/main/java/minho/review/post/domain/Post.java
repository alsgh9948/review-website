package minho.review.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import minho.review.common.validationgroup.CreateValidationGroup;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Post extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid_generator")
    @GenericGenerator(name = "uuid_generator", strategy = "uuid")
    private String id;

    @NotNull(groups= CreateValidationGroup.class)
    private String title;

    @NotNull(groups= CreateValidationGroup.class)
    private String contents;

    @Builder.Default
    @ColumnDefault("1")
    private int viewCount = 1;

    @NotNull(groups= CreateValidationGroup.class)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

}
