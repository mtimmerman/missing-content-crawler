package com.mtimmerman.security;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import java.util.Collections;

/**
 * Created by maarten on 16.01.15.
 */
public class PatchedAuthorizationServerSecurityConfigurer extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();

    private AccessDeniedHandler accessDeniedHandler = new OAuth2AccessDeniedHandler();

    private String realm = "oauth2/client";

    private boolean allowFormAuthenticationForClients = false;

    private String tokenKeyAccess = "denyAll()";

    private String checkTokenAccess = "denyAll()";

    public PatchedAuthorizationServerSecurityConfigurer allowFormAuthenticationForClients() {
        this.allowFormAuthenticationForClients = true;
        return this;
    }

    public PatchedAuthorizationServerSecurityConfigurer realm(String realm) {
        this.realm = realm;
        return this;
    }

    public PatchedAuthorizationServerSecurityConfigurer authenticationEntryPoint(
            AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        return this;
    }

    public PatchedAuthorizationServerSecurityConfigurer tokenKeyAccess(String tokenKeyAccess) {
        this.tokenKeyAccess = tokenKeyAccess;
        return this;
    }

    public PatchedAuthorizationServerSecurityConfigurer checkTokenAccess(String checkTokenAccess) {
        this.checkTokenAccess  = checkTokenAccess;
        return this;
    }

    public String getTokenKeyAccess() {
        return tokenKeyAccess;
    }

    public String getCheckTokenAccess() {
        return checkTokenAccess;
    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        registerDefaultAuthenticationEntryPoint(http);
        http.userDetailsService(new ClientDetailsUserDetailsService(clientDetailsService())).securityContext()
                .securityContextRepository(new NullSecurityContextRepository()).and().csrf().disable().httpBasic()
                .realmName(realm);
    }

    @SuppressWarnings("unchecked")
    private void registerDefaultAuthenticationEntryPoint(HttpSecurity http) {
        ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling = http
                .getConfigurer(ExceptionHandlingConfigurer.class);
        if (exceptionHandling == null) {
            return;
        }
        ContentNegotiationStrategy contentNegotiationStrategy = http.getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(contentNegotiationStrategy,
                MediaType.APPLICATION_ATOM_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA,
                MediaType.TEXT_XML);
        preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        exceptionHandling.defaultAuthenticationEntryPointFor(postProcess(authenticationEntryPoint), preferredMatcher);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // ensure this is initialized
        frameworkEndpointHandlerMapping();
        if (allowFormAuthenticationForClients) {
            clientCredentialsTokenEndpointFilter(http);
        }
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }

    private ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter(HttpSecurity http) {
        PatchedClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter = new PatchedClientCredentialsTokenEndpointFilter(
                frameworkEndpointHandlerMapping().getServletPath("/oauth/token"));
        clientCredentialsTokenEndpointFilter
                .setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        clientCredentialsTokenEndpointFilter = postProcess(clientCredentialsTokenEndpointFilter);
        http.addFilterBefore(clientCredentialsTokenEndpointFilter, BasicAuthenticationFilter.class);
        return clientCredentialsTokenEndpointFilter;
    }

    private ClientDetailsService clientDetailsService() {
        return getBuilder().getSharedObject(ClientDetailsService.class);
    }

    private FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping() {
        return getBuilder().getSharedObject(FrameworkEndpointHandlerMapping.class);
    }

}