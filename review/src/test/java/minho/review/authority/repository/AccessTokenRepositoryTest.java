package minho.review.authority.repository;

import minho.review.authority.domain.AccessToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AccessTokenRepositoryTest {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Test
    public void findNameTest(){

        AccessToken token = new AccessToken("diedjf","iiieie",3600);
        accessTokenRepository.save(token);

        Optional<AccessToken> findToken = accessTokenRepository.findByToken(token.getToken());
        findToken.ifPresent(accessToken -> Assertions.assertEquals(token.getToken(), accessToken.getToken()));
    }
}