package com.mtimmerman.security;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * Created by maarten on 16.01.15.
 */
public class PatchedAuthorizationServerConfigurerAdapter implements PatchedAuthorizationServerConfigurer {

    @Override
    public void configure(PatchedAuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    }

}
