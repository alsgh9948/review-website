package minho.review.user.service;

import lombok.RequiredArgsConstructor;
import minho.review.user.domain.User;
import minho.review.user.exception.DuplicateUserException;
import minho.review.user.exception.NotExistUserException;
import minho.review.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UUID join(User user){
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUser_uuid();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public UUID login(String id, String password){
        Optional<User> user = userRepository.findByIdAndPassword(id, password);
        if (user.isPresent()){
            return user.get().getUser_uuid();
        }
        else{
            throw new NotExistUserException("로그인 실패");
        }
    }

    public String findMyId(User user){
        Optional<User> findUser = userRepository.findByEmailAndPhone(user.getEmail(), user.getPhone());
        if (findUser.isPresent()){
            return findUser.get().getId();
        }
        else{
            throw new NotExistUserException("아이디 찾기 실패");
        }
    }

    public String findMyPassword(User user){
        Optional<User> findUser = userRepository.findByIdAndEmailAndPhone(user.getId(), user.getEmail(), user.getPhone());
        if (findUser.isPresent()){
            return findUser.get().getPassword();
        }
        else{
            throw new NotExistUserException("비밀번호 찾기 실패");
        }
    }

    public void validateDuplicateUser(User user){
        Optional<User> findUsers = userRepository.findByIdOrEmailAndPhone(user.getId(), user.getEmail(), user.getPhone());
        if (findUsers.isPresent()){
            throw new DuplicateUserException();
        }
    }
}
