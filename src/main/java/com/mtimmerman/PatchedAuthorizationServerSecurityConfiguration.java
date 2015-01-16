package com.mtimmerman;

/**
 * Created by maarten on 16.01.15.
 */

import com.mtimmerman.security.PatchedAuthorizationServerConfigurer;
import com.mtimmerman.security.PatchedAuthorizationServerSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;

import java.util.Collections;
import java.util.List;

/**
 * This class and all Patched* classes in com.guerdons.security are required because the default
 * ClientCredentialsTokenEndpointFilter also applies on the authorization_code grant_type flow.
 * As per the spec, the client_id is included by most libraries but the password isn't. That one is included in the
 * basic authorization header. So even while authentication is present and could succeed, the default
 * ClientCredentialsTokenEndpointFilter denies access before the BasicAuthenticationFilter can handle it.
 *
 * This chain of Patched* classes ensures that the ClientCredentialsTokenEndpointFilter isn't invoked when the
 * grant_type is authorization_code.
 */
@Configuration
public class PatchedAuthorizationServerSecurityConfiguration extends AuthorizationServerSecurityConfiguration {
    @Autowired
    private List<PatchedAuthorizationServerConfigurer> configurers = Collections.emptyList();
    @Autowired
    private AuthorizationServerEndpointsConfiguration endpoints;
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        PatchedAuthorizationServerSecurityConfigurer configurer = new PatchedAuthorizationServerSecurityConfigurer();
        FrameworkEndpointHandlerMapping handlerMapping = endpoints.oauth2EndpointHandlerMapping();
        http.setSharedObject(FrameworkEndpointHandlerMapping.class, handlerMapping);
        configure(configurer);
        http.apply(configurer);
        String tokenEndpointPath = handlerMapping.getServletPath("/oauth/token");
        String tokenKeyPath = handlerMapping.getServletPath("/oauth/token_key");
        String checkTokenPath = handlerMapping.getServletPath("/oauth/check_token");
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers(tokenEndpointPath).fullyAuthenticated()
                .antMatchers(tokenKeyPath).access(configurer.getTokenKeyAccess())
                .antMatchers(checkTokenPath).access(configurer.getCheckTokenAccess())
                .and()
                .requestMatchers()
                .antMatchers(tokenEndpointPath, tokenKeyPath, checkTokenPath);
        // @formatter:on
        http.setSharedObject(ClientDetailsService.class, clientDetailsService);
    }

    protected void configure(PatchedAuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        for (PatchedAuthorizationServerConfigurer configurer : configurers) {
            configurer.configure(oauthServer);
        }
    }
}