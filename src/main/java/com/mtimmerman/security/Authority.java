package com.mtimmerman.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by maarten on 13.01.15.
 */
public class Authority implements GrantedAuthority {

    private String authority;


    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
