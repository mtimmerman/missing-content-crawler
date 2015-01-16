package com.mtimmerman.security;

import com.mtimmerman.model.entities.AppUser;
import com.mtimmerman.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by maarten on 16.01.15.
 */
@Service
public class AppUserAccessDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(AppUserAccessDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(
                email
        );

        if (appUser == null) {
            logger.info(
                    "User not found"
            );

            throw new UsernameNotFoundException(
                    "User not found"
            );
        }

        return appUser;
    }
}
