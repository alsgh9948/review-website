package minho.review.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenExpiredTime;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public TokenProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.expired_time}") long tokenExpiredTime) {
        this.secret = secret;
        this.tokenExpiredTime = tokenExpiredTime;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secret);
        this.key = new SecretKeySpec(keyBytes,this.signatureAlgorithm.getJcaName());
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
}
