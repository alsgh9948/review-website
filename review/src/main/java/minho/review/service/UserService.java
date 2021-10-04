package minho.review.service;

import lombok.RequiredArgsConstructor;
import minho.review.domain.User;
import minho.review.repository.UserRepository;
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

        userRepository.save(user);
        return user.getUser_uuid();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public boolean login(String id, String password){
        Optional<User> user = userRepository.findByIdAndPassword(id, password);
        return user.isPresent();
    }

    public String findMyId(String email, String phone){
        Optional<User> user = userRepository.findByEmailAndPhone(email, phone);
        return user.map(User::getEmail).orElse("");
    }

    public String findMyPassword(String id, String email, String phone){
        Optional<User> user = userRepository.findByIdAndEmailAndPhone(id, email, phone);
        return user.map(User::getPassword).orElse("");
    }
}
