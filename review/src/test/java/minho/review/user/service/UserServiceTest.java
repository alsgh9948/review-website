package minho.review.user.service;

import minho.review.user.domain.User;
import minho.review.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Test
    void join() {
        User user = createUser();

        UUID save_uuid = userService.join(user);
        assertEquals(user, userRepository.findOne(save_uuid));
    }

    @Test
    void login() {
        User user = createUser();

        userService.join(user);
//        assertEquals(user.getUuid(),userService.login(user.getUsername(),user.getPassword()));
    }

    @Test
    void findMyId() {
        User user = createUser();
        userService.join(user);

        String findIdResult = userService.findMyId(user);
        assertEquals(user.getUsername(),findIdResult);
    }

    @Test
    void findMyPassword() {
        User user = createUser();
        userService.join(user);

        String findPasswordResult = userService.findMyPassword(user);
        assertEquals(user.getPassword(),findPasswordResult);
    }

    User createUser(){
        User user = new User();
        user.setUsername("tester");
        user.setPassword("123");
        user.setEmail("test@test.com");
        user.setPhone("01012341234");
        return user;
    }
}