package com.mtimmerman.repositories.oauth;

import com.mtimmerman.model.entities.oauth.OauthAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by maarten on 13.01.15.
 */
@RepositoryRestResource(exported = false)
public interface OauthAccessTokenRepository extends CrudRepository<OauthAccessToken, String> {
    List<OauthAccessToken> findByAuthenticationId(@Param("authenticationId") String authenticationId);

    List<OauthAccessToken> findByTokenId(@Param("tokenId") String tokenId);

    List<OauthAccessToken> findByTokenByRefreshToken(@Param("refreshToken") String refreshToken);

    List<OauthAccessToken> findByClientId(@Param("clientId") String clientId);

    List<OauthAccessToken> findByUserName(@Param("userName") String userName);

    List<OauthAccessToken> findByUserNameAndClientId(@Param("userName") String userName, @Param("clientId") String clientId);
}

