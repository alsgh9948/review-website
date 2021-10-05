package minho.review.user.repository;

import lombok.RequiredArgsConstructor;
import minho.review.user.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(UUID uuid){
        return em.find(User.class,uuid);
    }
    public List<User> findAll(){
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }

    public Optional<User> findByIdAndPassword(String id, String password){
        TypedQuery<User> user = em.createQuery("select u from User u where u.id = :id and u.password = :password", User.class)
                .setParameter("id",id)
                .setParameter("password",password);

        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByIdOrEmailAndPhone(String id,String email, String phone){
        TypedQuery<User> user = em.createQuery("select u from User u where u.id = :id or (u.email = :email and u.phone = :phone)", User.class)
                .setParameter("id",id)
                .setParameter("email",email)
                .setParameter("phone",phone);

        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByIdAndEmailAndPhone(String id,String email, String phone){
        TypedQuery<User> user = em.createQuery("select u from User u where u.id = :id and u.email = :email and u.phone = :phone", User.class)
                .setParameter("id",id)
                .setParameter("email",email)
                .setParameter("phone",phone);

        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByEmailAndPhone(String email, String phone){
        TypedQuery<User> user = em.createQuery("select u from User u where u.email = :email and u.phone = :phone", User.class)
                .setParameter("email",email)
                .setParameter("phone",phone);

        return user.getResultList().stream().findAny();
    }
}
