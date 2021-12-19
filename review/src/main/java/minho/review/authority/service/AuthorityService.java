package minho.review.authority.service;

import minho.review.authority.utils.RedisUtils;
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

    private final RedisUtils redisUtils;
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;

    public AuthorityService(AuthenticationManagerBuilder authenticationManagerBuilder,
                            TokenProvider tokenProvider,
                            RedisUtils redisUtils,
                            @Value("${jwt.access_token_expired_time}") long accessTokenExpiredTime,
                            @Value("${jwt.refresh_token_expired_time}") long refreshTokenExpiredTime) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.redisUtils = redisUtils;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }

    public String setRedisKey(String type, String id){
        if(type.equals("access")){
            return "Access:" +id;
        }
        else{
            return "Refresh:" +id;
        }
    }

    public Map<String, String> login(String username, String password, String id){

        String redisAccessTokenKey = setRedisKey("access",id);
        String redisRefreshTokenKey = setRedisKey("refresh",id);

        String remainAccessToken = redisUtils.getData(redisAccessTokenKey);
        if(remainAccessToken != null){
            setBlackListToken(remainAccessToken);
            redisUtils.deleteData(redisAccessTokenKey);
            redisUtils.deleteData(redisRefreshTokenKey);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        Map<String, String> jwt = new HashMap<>();

        jwt.put("accessToken",accessToken);
        jwt.put("refreshToken",refreshToken);

        redisUtils.setData(redisAccessTokenKey,accessToken,accessTokenExpiredTime);
        redisUtils.setData(redisRefreshTokenKey,refreshToken,refreshTokenExpiredTime);

        return jwt;
    }

    public void logout(String accessToken, String id){
        setBlackListToken(accessToken);
        redisUtils.deleteData(setRedisKey("access",id));
        redisUtils.deleteData(setRedisKey("refresh",id));
    }
    public void setBlackListToken(String accessToken){
        long expiredTime = tokenProvider.getExpiration(accessToken);
        redisUtils.setData(accessToken,"logout",expiredTime);
    }

    public Map<String, String> refreshAccessToken(String accessToken, User user){
        setBlackListToken(accessToken);

        Map<String, String> jwt = new HashMap<>();

        String refreshToken = redisUtils.getData(setRedisKey("refresh",user.getId()));
        if (tokenProvider.validateToken(refreshToken)) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String newAccessToken = tokenProvider.createToken(authentication);

            jwt.put("accessToken", newAccessToken);
        }
        return jwt;
    }
}
