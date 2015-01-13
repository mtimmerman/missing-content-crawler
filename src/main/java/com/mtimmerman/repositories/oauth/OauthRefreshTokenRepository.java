package com.mtimmerman.repositories.oauth;

import com.mtimmerman.model.entities.oauth.OauthRefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by maarten on 13.01.15.
 */
@RepositoryRestResource(exported = false)
public interface OauthRefreshTokenRepository extends CrudRepository<OauthRefreshToken, String> {
    List<OauthRefreshToken> findByTokenId(@Param("tokenId") String tokenId);
}
