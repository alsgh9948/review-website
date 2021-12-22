package minho.review.user.service;

import lombok.RequiredArgsConstructor;
import minho.review.authority.service.AuthorityService;
import minho.review.common.utils.Utils;
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
    public String join(User user){
        validateDuplicateUser(user);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public String updateUser(User user){
        validateDuplicateUser(user);

        User updateUser = userRepository.findById(user.getId()).orElseThrow(NotExistUserException::new);

        if (user.getPassword() != null){
            String encodePassword = passwordEncoder.encode(user.getPassword());
            updateUser.setPassword(encodePassword);
        }

        if(user.getEmail() != null){
            updateUser.setEmail(user.getEmail());
        }

        if(user.getPhone() != null){
            updateUser.setPhone(user.getPhone());
        }

        userRepository.save(updateUser);
        return updateUser.getId();
    }

    public User findById(String userId){
        return userRepository.findById(userId).orElseThrow(NotExistUserException::new);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Map<String, Object> login(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password,user.get().getPassword())){
            String userId = user.get().getId();

            Map<String, Object> data  = new HashMap<String, Object>();
            data.put("id",userId);

            Map<String, String> jwt = authorityService.login(username, password,userId);
            data.put("jwt",jwt);

            return data;
        }
        else{
            throw new NotExistUserException("로그인 실패");
        }
    }

    public void logout(String bearerToken, User user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(NotExistUserException::new);
        String accessToken = bearerToken.substring(7);
        authorityService.logout(accessToken, user.getId());
    }
    public String findMyId(User user){
        User findUser = userRepository.findByEmailAndPhone(user.getEmail(), user.getPhone()).orElseThrow(NotExistUserException::new);
        return findUser.getUsername();
    }

    @Transactional
    public String findMyPassword(User user){
        User findUser = userRepository.findByUsernameAndEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone())
                .orElseThrow(NotExistUserException::new);

        String temporaryPassword = new Utils().getRamdomPassword(8);
        String encodedPassword = passwordEncoder.encode(temporaryPassword);

        findUser.setPassword(encodedPassword);

        userRepository.save(findUser);
        return temporaryPassword;
    }

    public void validateDuplicateUser(User user){
        Optional<User> findUsers = userRepository.findByUsernameOrEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone());
        if (findUsers.isPresent()){
            throw new DuplicateUserException();
        }
    }

    public Map<String, String> refreshAccessToken(String bearerToken, User user){
        String accessToken = bearerToken.substring(7);
        User findUser = userRepository.findById(user.getId()).orElseThrow(NotExistUserException::new);
        return authorityService.refreshAccessToken(accessToken, findUser);
    }
}
