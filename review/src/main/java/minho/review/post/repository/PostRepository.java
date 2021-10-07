package minho.review.post.repository;

import lombok.RequiredArgsConstructor;
import minho.review.post.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post){
        if (post.getUuid() == null) {
            em.persist(post);
        }
        else {
            em.merge(post);
        }
    }

    public void delete(UUID uuid){
        Post deletePost = findOne(uuid);
        em.remove(deletePost);
    }

    public Post findOne(UUID uuid){
        return em.find(Post.class,uuid);
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public Optional<Post> findTitle(String title){
        TypedQuery<Post> post = em.createQuery("select p from Post p where p.title = :title",Post.class)
                .setParameter("title", title);

        return post.getResultList().stream().findAny();
    }
}