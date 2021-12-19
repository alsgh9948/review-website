package minho.review.user.service;

import lombok.RequiredArgsConstructor;
import minho.review.common.jwt.CustomUserDetails;
import minho.review.user.domain.User;
import minho.review.user.exception.NotExistUserException;
import minho.review.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return new CustomUserDetails(user.get());
        }
        else{
            throw new UsernameNotFoundException("Not Found User");
        }
//        return new CustomUserDetails(userRepository.findByUsername(username));
//                .map(this::createUser)
//                .orElseThrow(NotExistUserException::new);
    }

//    private org.springframework.security.core.userdetails.User createUser(User user){
////        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
////        grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRole().getValue()));
//
//        return new UserDetails(user);
//    }
}
