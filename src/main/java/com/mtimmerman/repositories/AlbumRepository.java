package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Album;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface AlbumRepository extends PagingAndSortingRepository<Album, Integer> {
    Album findByLastFMName(
            @Param("lastFMName") String lastFMName
    );
}
