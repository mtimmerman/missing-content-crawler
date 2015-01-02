package com.mtimmerman;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created by maarten on 02.01.15.
 */
@Configuration
@EnableWebMvcSecurity
public class SecuriityConfiguration {
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    protected static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.antMatcher("/**").authorizeRequests().antMatchers("/**").permitAll();
        }
    }
}
