package minho.review.user.service;

import lombok.RequiredArgsConstructor;
import minho.review.authority.service.AuthorityService;
import minho.review.user.domain.User;
import minho.review.user.exception.DuplicateUserException;
import minho.review.user.exception.NotExistUserException;
import minho.review.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final AuthorityService authorityService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UUID join(User user){
        validateDuplicateUser(user);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user.getUuid();
    }

    @Transactional
    public UUID updateUser(User user){
        validateDuplicateUser(user);

        User findUser = userRepository.findOne(user.getUuid());

        if (user.getPassword() != null){
            String encodePassword = passwordEncoder.encode(user.getPassword());
            findUser.setPassword(encodePassword);
        }

        if(user.getEmail() != null){
            findUser.setEmail(user.getEmail());
        }

        if(user.getPhone() != null){
            findUser.setPhone(user.getPhone());
        }

        return findUser.getUuid();
    }

    public User findOne(UUID uuid){
        return userRepository.findOne(uuid);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Map<String, Object> login(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password,user.get().getPassword())){
            String user_uuid = user.get().getUuid().toString();

            Map<String, Object> data  = new HashMap<String, Object>();
            data.put("uuid",user_uuid);

            Map<String, String> jwt = authorityService.login(username, password,user_uuid);
            data.put("jwt",jwt);

            return data;
        }
        else{
            throw new NotExistUserException("로그인 실패");
        }
    }

    public void logout(String bearerToken, User user){
        User findUser = userRepository.findOne(user.getUuid());
        if (findUser != null){
            String accessToken = bearerToken.substring(7);
            authorityService.logout(accessToken, user.getUuid().toString());
        }
        else{
            throw new NotExistUserException();
        }
    }
    public String findMyId(User user){
        Optional<User> findUser = userRepository.findByEmailAndPhone(user.getEmail(), user.getPhone());
        if (findUser.isPresent()){
            return findUser.get().getUsername();
        }
        else{

            throw new NotExistUserException("아이디 찾기 실패");
        }
    }

    public String findMyPassword(User user){
        Optional<User> findUser = userRepository.findByUsernameAndEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone());
        if (findUser.isPresent()){
            return findUser.get().getPassword();
        }
        else{
            throw new NotExistUserException("비밀번호 찾기 실패");
        }
    }

    public void validateDuplicateUser(User user){
        Optional<User> findUsers = userRepository.findByUsernameOrEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone());
        if (findUsers.isPresent()){
            throw new DuplicateUserException();
        }
    }

    public Map<String, String> refreshAccessToken(String accessToken){
        return authorityService.refreshAccessToken(accessToken);
    }
}
