package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 16.01.15.
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<AppUser, Integer> {
    public AppUser findByEmail(
            @Param("email") String email
    );
}
