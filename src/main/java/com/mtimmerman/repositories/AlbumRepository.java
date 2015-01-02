package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.model.entities.Artist;
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
public interface AlbumRepository extends PagingAndSortingRepository<Album, Integer> {
    Album findByLastFMName(
            @Param("lastFMName") String lastFMName
    );

    @Query("" +
            "SELECT al " +
            "FROM Album al " +
            "JOIN al.artist ar " +
            "WHERE al.plexKey IS NULL " +
            "GROUP BY ar.lastFMName, al.lastFMName, al.id " +
            "ORDER BY ar.lastFMName, al.lastFMName")
    Page<Album> findNotOnPlex(Pageable pageRequest);

    List<Album> findByArtistOrderByLastFMNameAsc(
            @Param("artist") Artist artist
    );

    @Query("" +
            "SELECT al " +
            "FROM Album al " +
            "WHERE al.artist = :artist " +
            "AND al.plexKey IS NULL " +
            "ORDER BY al.lastFMName")
    List<Album> findByArtistNotOnPlex(
            @Param("artist") Artist artist
    );
}
