package minho.review.post.repository;

import minho.review.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    Optional<Post> findByTitle(String title);
    List<Post> findByUser_IdOrderByCreateDate(String id);
}