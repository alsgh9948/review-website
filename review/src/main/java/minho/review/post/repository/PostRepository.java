package minho.review.post.repository;

import minho.review.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    Optional<Post> findByTitle(String title);
    List<Post> findByUser_IdOrderByCreateDate(String id);

    @Modifying
    @Query(value = "update post p set p.view_count = p.view_count + 1 where p.id = :id", nativeQuery = true)
    void updateViewCount(@Param("id") String id);
}