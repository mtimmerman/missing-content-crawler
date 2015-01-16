package com.mtimmerman.security;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * Created by maarten on 16.01.15.
 */
public interface PatchedAuthorizationServerConfigurer {

    /**
     * Configure the security of the Authorization Server, which means in practical terms the /oauth/token endpoint. The
     * /oauth/authorize endpoint also needs to be secure, but that is a normal user-facing endpoint and should be
     * secured the same way as the rest of your UI, so is not covered here. The default settings cover the most common
     * requirements, following recommendations from the OAuth2 spec, so you don't need to do anything here to get a
     * basic server up and running.
     *
     * @param security a fluent configurer for security features
     */
    void configure(PatchedAuthorizationServerSecurityConfigurer security) throws Exception;

    /**
     * Configure the {@link org.springframework.security.oauth2.provider.ClientDetailsService}, e.g. declaring individual clients and their properties. Note that
     * password grant is not enabled (even if some clients are allowed it) unless an {@link org.springframework.security.authentication.AuthenticationManager} is
     * supplied to the {@link #configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)}. At least one client, or a fully
     * formed custom {@link org.springframework.security.oauth2.provider.ClientDetailsService} must be declared or the server will not start.
     *
     * @param clients the client details configurer
     */
    void configure(ClientDetailsServiceConfigurer clients) throws Exception;

    /**
     * Configure the non-security features of the Authorization Server endpoints, like token store, token
     * customizations, user approvals and grant types. You shouldn't need to do anything by default, unless you need
     * password grants, in which case you need to provide an {@link org.springframework.security.authentication.AuthenticationManager}.
     *
     * @param endpoints the endpoints configurer
     */
    void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception;

}