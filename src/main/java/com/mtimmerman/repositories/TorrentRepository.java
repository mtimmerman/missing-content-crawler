package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.Torrent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface TorrentRepository extends PagingAndSortingRepository<Torrent, Integer> {
    Torrent findByTorrentId(
            @Param("torrentId") String torrentId
    );
}
