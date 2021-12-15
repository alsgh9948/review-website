package minho.review.authority.domain;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash(value = "refresh")
public class RefreshToken {

    @Id
    private String id;
    @Indexed
    private String token;
    @TimeToLive
    private int expired;

    public RefreshToken(String id, String token, int expired) {
        this.id = id;
        this.token = token;
        this.expired = expired;
    }
}
