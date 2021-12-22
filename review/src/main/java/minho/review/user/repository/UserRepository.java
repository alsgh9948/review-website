package minho.review.user.repository;

import minho.review.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("select u from User u where username= :username or (email = :email and phone = :phone)")
    Optional<User> findByUsernameOrEmailAndPhone(@Param("username") String username,
                                                 @Param("email") String email,
                                                 @Param("phone") String phone);

    Optional<User> findByUsernameAndEmailAndPhone(String username, String email, String phone);

    Optional<User> findByEmailAndPhone(String email, String phone);
}
