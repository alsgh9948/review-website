package minho.review.user.service;

import lombok.RequiredArgsConstructor;
import minho.review.authority.dto.TokenDto;
import minho.review.authority.service.AuthorityService;
import minho.review.common.utils.Utils;
import minho.review.user.domain.User;
import minho.review.user.dto.UserDto;
import minho.review.user.exception.DuplicateUserException;
import minho.review.user.exception.NotExistUserException;
import minho.review.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final AuthorityService authorityService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.Response join(UserDto.Request joinRequest){
        validateDuplicateUser(joinRequest);

        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());
        joinRequest.setPassword(encodedPassword);

        User info = userRepository.save(joinRequest.toEntity());
        return new UserDto.Response(info,joinRequest.getRole());
    }

    @Transactional
    public UserDto.Response updateUser(UserDto.Request user){
//        validateDuplicateUser(user);

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

        return new UserDto.Response(userRepository.save(updateUser));
    }

    public UserDto.Response findById(String userId){
        User user = userRepository.findById(userId).orElseThrow(NotExistUserException::new);
        return new UserDto.Response(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public TokenDto.RepsonseAll login(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password,user.get().getPassword())){
            String userId = user.get().getId();

            TokenDto.RepsonseAll response = authorityService.login(username, password,userId);
            return response;
        }
        else{
            throw new NotExistUserException("로그인 실패");
        }
    }

    public void logout(String bearerToken, UserDto.Request user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(NotExistUserException::new);
        String accessToken = bearerToken.substring(7);
        authorityService.logout(accessToken, user.getId());
    }
    public String findMyId(UserDto.Request user){
        User findUser = userRepository.findByEmailAndPhone(user.getEmail(), user.getPhone()).orElseThrow(NotExistUserException::new);
        return findUser.getUsername();
    }

    @Transactional
    public String findMyPassword(UserDto.Request user){
        User findUser = userRepository.findByUsernameAndEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone())
                .orElseThrow(NotExistUserException::new);

        String temporaryPassword = new Utils().getRamdomPassword(8);
        String encodedPassword = passwordEncoder.encode(temporaryPassword);

        findUser.setPassword(encodedPassword);

        userRepository.save(findUser);
        return temporaryPassword;
    }

    public void validateDuplicateUser(UserDto.Request user){
        Optional<User> findUsers = userRepository.findByUsernameOrEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone());
        if (findUsers.isPresent()){
            throw new DuplicateUserException();
        }
    }

    public TokenDto.Repsonse refreshAccessToken(String bearerToken, UserDto.Request user){
        String accessToken = bearerToken.substring(7);
        User findUser = userRepository.findById(user.getId()).orElseThrow(NotExistUserException::new);
        return authorityService.refreshAccessToken(accessToken, findUser);
    }
}
