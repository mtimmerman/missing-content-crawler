package com.mtimmerman.service;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.model.entities.Artist;
import com.mtimmerman.repositories.AlbumRepository;
import com.mtimmerman.repositories.ArtistRepository;
import com.mtimmerman.rest.resources.lastfm.AlbumList;
import com.mtimmerman.rest.resources.lastfm.LastFMAlbum;
import com.mtimmerman.rest.resources.plex.Directory;
import com.mtimmerman.rest.resources.plex.DirectoryList;
import com.mtimmerman.rest.resources.plex.Server;
import com.mtimmerman.rest.resources.plex.enums.DirectoryType;
import com.mtimmerman.service.exceptions.GapCrawlerException;
import com.mtimmerman.service.exceptions.LastFMException;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by maarten on 05.01.15.
 */
@Component
@Scope("prototype")
public class MusicCrawler extends AbstractCrawler {
    @Autowired
    private LastFMConnector lastFMConnector;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;

    private AlbumList getLastFMAlbums(String artist) throws IOException, LastFMException
    {
        return lastFMConnector.getArtistGetTopAlbums(
                artist,
                null,
                true,
                0,
                999
        );
    }


    @Override
    void process() throws
            PlexServerNotFoundException,
            IOException,
            GapCrawlerException,
            LastFMException {
        Server server = plexConnector.getServer(
                getServerName()
        );

        DirectoryList root = plexConnector.getSections(
                server,
                null,
                null
        );

        for (Directory rootDirectory: root.getDirectories()) {
            if (rootDirectory.getType() == DirectoryType.artist) {
                DirectoryList artistDirectoryList = plexConnector.getSections(
                        server,
                        root,
                        rootDirectory.getKey()
                );

                for (Directory musicDirectory: artistDirectoryList.getDirectories()) {
                    if (musicDirectory.getKey().equals("all")) {
                        DirectoryList allArtistsDirectoryList = plexConnector.getSections(
                                server,
                                artistDirectoryList,
                                musicDirectory.getKey()
                        );

                        for (Directory artistDirectory: allArtistsDirectoryList.getDirectories()) {
                            logInfo(
                                    String.format(
                                            "ARTIST: %s",
                                            artistDirectory.getTitle()
                                    )
                            );

                            AlbumList lastFMAlbumList = getLastFMAlbums(
                                    artistDirectory.getTitle()
                            );

                            if (lastFMAlbumList.getLastFMAlbums() == null) {
                                lastFMAlbumList = getLastFMAlbums(
                                        lastFMAlbumList.getArtist()
                                );
                            }

                            Artist artist = artistRepository.findByPlexKey(
                                    artistDirectory.getKey()
                            );

                            Boolean changed = Boolean.FALSE;

                            if (artist == null)
                            {
                                artist = new Artist();

                                artist.setPlexKey(
                                        artistDirectory.getKey()
                                );
                                artist.setPlexName(
                                        artistDirectory.getTitle()
                                );

                                changed = Boolean.TRUE;
                            }

                            if (artist.getLastFMName() == null || !artist.getLastFMName().equals(lastFMAlbumList.getArtist())) {
                                artist.setLastFMName(
                                        lastFMAlbumList.getArtist()
                                );

                                changed = Boolean.TRUE;
                            }

                            if (changed) {
                                artistRepository.save(artist);
                            }

                            if (lastFMAlbumList.getLastFMAlbums() != null) {
                                DirectoryList plexAlbumList = plexConnector.getMetaData(
                                        server,
                                        artistDirectory.getKey()
                                );

                                if (plexAlbumList.getDirectories() != null) {
                                    for (LastFMAlbum lastFMAlbum : lastFMAlbumList.getLastFMAlbums()) {
                                        logInfo(
                                                String.format(
                                                        "\tALBUM: %s",
                                                        lastFMAlbum.getName()
                                                )
                                        );

                                        Album album = albumRepository.findByLastFMName(
                                                lastFMAlbum.getName()
                                        );

                                        changed = Boolean.FALSE;

                                        if (album == null) {
                                            album = new Album();
                                            album.setArtist(
                                                    artist
                                            );
                                            album.setLastFMmbId(
                                                    lastFMAlbum.getMbid()
                                            );
                                            album.setLastFMName(
                                                    lastFMAlbum.getName()
                                            );

                                            changed = Boolean.TRUE;
                                        }

                                        if (album.getPlexKey() == null) {
                                            Boolean foundOnPlex = Boolean.FALSE;

                                            for (Directory albumDirectory : plexAlbumList.getDirectories()) {
                                                if (albumDirectory.getType() == DirectoryType.album) {
                                                    if (albumDirectory.getTitle().equals(lastFMAlbum.getName())) {
                                                        album.setPlexKey(
                                                                albumDirectory.getKey()
                                                        );
                                                        album.setPlexName(
                                                                albumDirectory.getTitle()
                                                        );

                                                        changed = Boolean.TRUE;
                                                        foundOnPlex = Boolean.TRUE;

                                                        break;
                                                    }
                                                }
                                            }

                                            logInfo(
                                                    String.format(
                                                            "\t\tFound on plex: %s",
                                                            foundOnPlex ? "YES" : "NO"
                                                    )
                                            );
                                        }

                                        if (changed) {
                                            albumRepository.save(
                                                    album
                                            );
                                        }
                                    }
                                }
                            } else {
                                logWarn(
                                        String.format(
                                                "Artist \"%s\" had no albums on LastFM. Corrected to: \"%s\"",
                                                artistDirectory.getTitle(),
                                                lastFMAlbumList.getArtist()
                                        )
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}
