package minho.review.service;

import minho.review.domain.User;
import minho.review.repository.UserRepository;
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
        assertTrue(userService.login(user.getId(),user.getPassword()));
        assertFalse(userService.login("test","1234"));
    }

    @Test
    void findMyId() {
        User user = createUser();
        userService.join(user);

        String findIdResult = userService.findMyId(user.getEmail(),user.getPhone());
        assertEquals(user.getId(),findIdResult);
    }

    @Test
    void findMyPassword() {
        User user = createUser();
        userService.join(user);

        String findPasswordResult = userService.findMyPassword(user.getId(),user.getEmail(),user.getPhone());
        assertEquals(user.getPassword(),findPasswordResult);
    }

    User createUser(){
        User user = new User();
        user.setId("tester");
        user.setPassword("123");
        user.setEmail("test@test.com");
        user.setPhone("01012341234");
        return user;
    }
}