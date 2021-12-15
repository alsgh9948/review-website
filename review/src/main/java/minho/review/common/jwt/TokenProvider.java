package minho.review.common.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import minho.review.authority.exception.ExpiredTokenException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component

public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenExpiredTime;
    private final long refreshTokenExpiredTime;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.access_token_expired_time}") long tokenExpiredTime,
                         @Value("${jwt.refresh_token_expired_time}") long refreshTokenExpiredTime) {
        this.secret = secret;
        this.tokenExpiredTime = tokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secret);
        this.key = new SecretKeySpec(keyBytes,this.signatureAlgorithm.getJcaName());
    }

    public String createRefreshToken(Authentication authentication){
        long now = (new Date()).getTime();
        Date expiredTime = new Date(now + this.refreshTokenExpiredTime);

        CustomUserDetails customUserDetails = ((CustomUserDetails) authentication.getPrincipal());
        return Jwts.builder()
                .setSubject(customUserDetails.getUser().getUuid().toString())
                .signWith(signatureAlgorithm,key)
                .setExpiration(expiredTime)
                .compact();
    }
    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date expiredTime = new Date(now + this.tokenExpiredTime);

        return Jwts.builder()
               .setSubject(authentication.getName())
               .claim(AUTHORITIES_KEY, authorities)
               .signWith(signatureAlgorithm,key)
               .setExpiration(expiredTime)
               .compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        }  catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }
}
