package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Integer> {
    Episode findBySeasonAndTheTVDbEpisodeName(
            @Param("season") Season season,
            @Param("theTVDbEpisodeName") String theTVDbEpisodeName
    );

    @Query("" +
           "SELECT e " +
           "FROM Episode e " +
           "JOIN e.season s " +
           "JOIN s.tvShow t " +
           "WHERE e.plexKey IS NULL " +
           "AND s.theTVDbSeasonNumber > 0 " +
           "GROUP BY t.theTVDbName, s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber, e.id " +
           "ORDER BY t.theTVDbName, s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber")
    Page<Episode> findNotOnPlex(
            Pageable pageable
    );

    List<Episode> findBySeasonOrderByTheTVDbEpisodeNumberAsc(
            @Param("season") Season season
    );

    @Query("" +
            "SELECT e " +
            "FROM Episode e " +
            "WHERE e.season = :season " +
            "AND e.plexKey IS NULL " +
            "ORDER BY e.theTVDbEpisodeNumber")
    List<Episode> findBySeasonNotOnPlex(
            @Param("season") Season season
    );

    @Query("SELECT e " +
            "FROM Episode e " +
            "JOIN e.season s " +
            "WHERE s.tvShow = :tvShow " +
            "GROUP BY s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber, e.id " +
            "ORDER BY s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber")
    List<Episode> findByTvShow(
            @Param("tvShow") TvShow tvShow
    );

    @Query("SELECT e " +
            "FROM Episode e " +
            "JOIN e.season s " +
            "WHERE s.tvShow = :tvShow " +
            "AND e.plexKey IS NULL " +
            "AND s.theTVDbSeasonNumber > 0 " +
            "GROUP BY s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber, e.id " +
            "ORDER BY s.theTVDbSeasonNumber, e.theTVDbEpisodeNumber")
    List<Episode> findByTvShowNotOnPlex(
            @Param("tvShow") TvShow tvShow
    );
}
