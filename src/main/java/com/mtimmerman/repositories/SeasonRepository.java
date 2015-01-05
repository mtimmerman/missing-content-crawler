package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface SeasonRepository extends PagingAndSortingRepository<Season, Integer> {
    Season findByTvShowAndTheTVDbSeasonNumber(
            @Param("tvShow") TvShow tvShow,
            @Param("theTVDbSeasonNumber") Integer theTVDbSeasonNumber
    );

    List<Season> findByTvShowOrderByTheTVDbSeasonNumberAsc(
            @Param("tvShow") TvShow tvShow
    );
}
