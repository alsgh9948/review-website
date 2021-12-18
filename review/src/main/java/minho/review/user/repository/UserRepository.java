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
        if (user.getUuid() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public User findOne(UUID uuid){
        return em.find(User.class,uuid);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }

    public Optional<User> findByUsername(String username){
        TypedQuery<User> user = em.createQuery("select u from User u where u.username = :username",User.class)
                .setParameter("username",username);
        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByUsernameAndPassword(String username, String password){
        TypedQuery<User> user = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username",username)
                .setParameter("password",password);

        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByUsernameOrEmailAndPhone(String username,String email, String phone){
        TypedQuery<User> user = em.createQuery("select u from User u where u.username = :username or (u.email = :email and u.phone = :phone)", User.class)
                .setParameter("username",username)
                .setParameter("email",email)
                .setParameter("phone",phone);

        return user.getResultList().stream().findAny();
    }

    public Optional<User> findByUsernameAndEmailAndPhone(String username,String email, String phone){
        TypedQuery<User> user = em.createQuery("select u from User u where u.username = :username and u.email = :email and u.phone = :phone", User.class)
                .setParameter("username",username)
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
