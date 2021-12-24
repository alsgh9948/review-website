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

import java.util.ArrayList;
import java.util.List;

@Component("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::createUser)
                .orElseThrow(NotExistUserException::new);
    }

    private CustomUserDetails createUser(User user){
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRole().getValue()));

        return new CustomUserDetails(user,grantedAuthorityList);
    }
}
