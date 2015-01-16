package com.mtimmerman;

import com.mtimmerman.filters.CORSFilter;
import com.mtimmerman.filters.InternationalizationFilter;
import com.mtimmerman.security.NoRedirectLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by maarten on 13.01.15.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration {
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 2)
    @Configuration
    protected static class OauthSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private DataSource dataSource;

        private AccessDeniedHandler accessDeniedHandler = new OAuth2AccessDeniedHandler();

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            ResourceServerSecurityConfigurer resources = new ResourceServerSecurityConfigurer();
            resources.tokenStore(new JdbcTokenStore(dataSource));

            http.requestMatchers().antMatchers(
                    "/api/**"
            ).and()
                    .addFilterBefore(
                            new CORSFilter(),
                            ChannelProcessingFilter.class
                    )
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()
                    .antMatchers(
                            HttpMethod.OPTIONS,
                            "/api/**"
                    ).permitAll()
                    .antMatchers(
                            "/api/**"
                    ).authenticated()
                    .and().apply(resources)
                    .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
    @Configuration
    protected static class ErrorSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/error")
                    .anonymous()
                    .and().csrf().disable()
                    .authorizeRequests().antMatchers("/error").permitAll();
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    protected static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private NoRedirectLogoutSuccessHandler noRedirectLogoutSuccessHandler;
        @Autowired
        private InternationalizationFilter internationalizationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/logout")
                    .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
            http.antMatcher("/**")
                    .addFilterBefore(internationalizationFilter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .and().formLogin().loginPage("/login").permitAll()
                    .and().logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(noRedirectLogoutSuccessHandler).permitAll()
                    .and().authorizeRequests()
                    .antMatchers("/oauth/authorize").authenticated()
                    .antMatchers("/static/**").permitAll()
                    .antMatchers("/health", "/info").permitAll()
                    .antMatchers(
                            "/autoconfig",
                            "/beans",
                            "/configprops",
                            "/dump",
                            "/env",
                            "/metrics",
                            "/mappings",
                            "/shutdown",
                            "/trace").hasRole("MANAGEMENT")
                    .antMatchers("/**").authenticated();
        }
    }

}
