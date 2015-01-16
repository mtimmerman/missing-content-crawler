package com.mtimmerman.repositories.oauth;

import com.mtimmerman.model.entities.oauth.OauthClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 13.01.15.
 */
@RepositoryRestResource(exported = false)
public interface OauthClientDetailsRepository extends CrudRepository<OauthClientDetails, String> {
    OauthClientDetails findByClientId(@Param("clientId") String clientId);
}
