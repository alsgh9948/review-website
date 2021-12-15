package minho.review.authority.service;

import minho.review.authority.domain.AccessToken;
import minho.review.authority.domain.RefreshToken;
import minho.review.authority.repository.AccessTokenRepository;
import minho.review.authority.repository.RefreshTokenRepository;
import minho.review.common.jwt.TokenProvider;
import minho.review.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorityService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final int accessTokenExpiredTime;
    private final int refreshTokenExpiredTime;

    public AuthorityService(AuthenticationManagerBuilder authenticationManagerBuilder,
                            TokenProvider tokenProvider,
                            AccessTokenRepository accessTokenRepository,
                            RefreshTokenRepository refreshTokenRepository,
                            @Value("${spring.redis.access_token_expired_time}") int accessTokenExpiredTime,
                            @Value("${spring.redis.refresh_token_expired_time}") int refreshTokenExpiredTime) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.accessTokenRepository = accessTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }

    public Map<String, String> initializeToken(User user, String uuid){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        Map<String, String> jwt = new HashMap<>();

        jwt.put("accessToken",accessToken);
        jwt.put("refreshToken",refreshToken);

        AccessToken redisAccessToken = new AccessToken(uuid, accessToken, accessTokenExpiredTime);
        accessTokenRepository.save(redisAccessToken);

        RefreshToken redisRefreshToken = new RefreshToken(uuid, refreshToken, refreshTokenExpiredTime);
        refreshTokenRepository.save(redisRefreshToken);

        return jwt;
    }

    public Map<String, String> refreshAccessToken(String accessToken){
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String newAccessToken = tokenProvider.createToken(authentication);

        Map<String, String> jwt = new HashMap<>();

        jwt.put("accessToken",newAccessToken);

        return jwt;
    }

    public void deleteAccessAndRefreshToken(String uuid){
        accessTokenRepository.deleteById(uuid);
        refreshTokenRepository.deleteById(uuid);
    }
}
