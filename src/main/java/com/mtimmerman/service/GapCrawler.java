package com.mtimmerman.service;

import com.mtimmerman.rest.resources.lastfm.Album;
import com.mtimmerman.rest.resources.lastfm.AlbumList;
import com.mtimmerman.rest.resources.plex.Directory;
import com.mtimmerman.rest.resources.plex.DirectoryList;
import com.mtimmerman.rest.resources.plex.Server;
import com.mtimmerman.rest.resources.plex.enums.DirectoryType;
import com.mtimmerman.service.exceptions.LastFMException;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by maarten on 29.12.14.
 */
@Component
public class GapCrawler {
    @Autowired
    PlexConnector plexConnector;
    @Autowired
    LastFMConnector lastFMConnector;
    @Autowired
    private ConfigurableEnvironment env;

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

    public void findGapsInMusic()
            throws IOException, PlexServerNotFoundException, LastFMException {
        Server server = plexConnector.getServer(
                env.getRequiredProperty("plex.server")
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
                            System.out.println(artistDirectory.getTitle());

                            AlbumList lastFMAlbumList = getLastFMAlbums(
                                    artistDirectory.getTitle()
                            );

                            if (lastFMAlbumList.getAlbums() == null) {
                                lastFMAlbumList = getLastFMAlbums(lastFMAlbumList.getArtist());
                            }

                            if (lastFMAlbumList.getAlbums() != null) {
                                DirectoryList plexAlbumList = plexConnector.getMetaData(
                                        server,
                                        artistDirectory.getKey()
                                );

                                if (plexAlbumList.getDirectories() != null) {
                                    for (Album album : lastFMAlbumList.getAlbums()) {
                                        for (Directory albumDirectory: plexAlbumList.getDirectories()) {
                                            if (albumDirectory.getType() == DirectoryType.album) {
                                                if (albumDirectory.getTitle().equals(album.getName())) {
                                                    album.setFoundInPlex(true);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    for (Album album: lastFMAlbumList.getAlbums())
                                    {
                                        if (!album.getFoundInPlex()) {
                                            System.out.println(
                                                    String.format(
                                                            "Album \"%s\" found on last FM but not on Plex",
                                                            album.getName()
                                                    )
                                            );
                                        }
                                    }
                                }
                            } else {
                                System.out.println(
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
