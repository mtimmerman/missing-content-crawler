package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Integer> {
    Artist findByPlexKey(
            @Param("plexKey") String plexKey
    );
}
