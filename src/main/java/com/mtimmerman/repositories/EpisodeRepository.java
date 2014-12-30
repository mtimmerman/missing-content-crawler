package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Integer> {
    Episode findBySeasonAndTheTVDbEpisodeName(
            @Param("season") Season season,
            @Param("theTVDbEpisodeName") String theTVDbEpisodeName
    );
}
