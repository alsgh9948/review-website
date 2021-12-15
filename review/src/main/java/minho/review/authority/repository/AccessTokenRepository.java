package minho.review.authority.repository;

import minho.review.authority.domain.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    public Optional<AccessToken> findByToken(String token);
}
