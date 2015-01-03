package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface TvShowRepository extends PagingAndSortingRepository<TvShow, Integer> {
    TvShow findByPlexKey(
            @Param("plexKey") String plexKey
    );


    @Query(
            "SELECT t " +
            "FROM TvShow t " +
            "WHERE lower(t.theTVDbName) like :theTVDbName"
    )
    Page<TvShow> findByTheTVDbNameLike(
            @Param("theTVDbName") String theTVDbName,
            Pageable pageable
    );
}
