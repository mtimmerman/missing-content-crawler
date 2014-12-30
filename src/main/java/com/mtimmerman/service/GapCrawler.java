package com.mtimmerman.service;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.model.entities.Artist;
import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.repositories.AlbumRepository;
import com.mtimmerman.repositories.ArtistRepository;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.repositories.SeasonRepository;
import com.mtimmerman.repositories.TvShowRepository;
import com.mtimmerman.rest.resources.lastfm.AlbumList;
import com.mtimmerman.rest.resources.lastfm.LastFMAlbum;
import com.mtimmerman.rest.resources.plex.Directory;
import com.mtimmerman.rest.resources.plex.DirectoryList;
import com.mtimmerman.rest.resources.plex.Server;
import com.mtimmerman.rest.resources.plex.Video;
import com.mtimmerman.rest.resources.plex.enums.DirectoryType;
import com.mtimmerman.rest.resources.thetvdb.BaseEpisodeRecord;
import com.mtimmerman.rest.resources.thetvdb.FullSeriesRecord;
import com.mtimmerman.service.exceptions.GapCrawlerException;
import com.mtimmerman.service.exceptions.LastFMException;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    TheTVDBConnector theTVDBConnector;
    @Autowired
    private ConfigurableEnvironment env;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

    private static final Logger log = LoggerFactory.getLogger(
            GapCrawler.class
    );

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
                            log.info(
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
                                        log.info(
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

                                            log.info(
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
                                log.warn(
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

    public void findGapsInTvEpisodes()
            throws IOException, PlexServerNotFoundException, TheTVDBConnectorException, GapCrawlerException {

        Server server = plexConnector.getServer(
                env.getRequiredProperty("plex.server")
        );

        DirectoryList root = plexConnector.getSections(
                server,
                null,
                null
        );

        for (Directory rootDirectory: root.getDirectories()) {
            if (rootDirectory.getType() == DirectoryType.show) {
                DirectoryList showDirectoryList = plexConnector.getSections(
                        server,
                        root,
                        rootDirectory.getKey()
                );

                for (Directory showDirectory: showDirectoryList.getDirectories()) {
                    if (showDirectory.getKey().equals("all")) {
                        DirectoryList allTVShowsDirectoryList = plexConnector.getSections(
                                server,
                                showDirectoryList,
                                showDirectory.getKey()
                        );

                        for (Directory tvShowDirectory: allTVShowsDirectoryList.getDirectories()) {
                            if (tvShowDirectory.getType() == DirectoryType.show) {
                                log.info(
                                        String.format(
                                                "TV SHOW: %s",
                                                tvShowDirectory.getTitle()
                                        )
                                );

                                FullSeriesRecord fullSeriesRecord = theTVDBConnector.getSeries(
                                        tvShowDirectory.getTitle()
                                );

                                if (fullSeriesRecord != null) {

                                    TvShow tvShow = tvShowRepository.findByPlexKey(
                                            tvShowDirectory.getKey()
                                    );

                                    Boolean changed = Boolean.FALSE;

                                    if (tvShow == null) {
                                        tvShow = new TvShow();

                                        tvShow.setPlexKey(
                                                tvShowDirectory.getKey()
                                        );

                                        tvShow.setPlexName(
                                                tvShowDirectory.getTitle()
                                        );

                                        changed = Boolean.TRUE;
                                    }

                                    if (tvShow.getTheTVDbName() == null || !tvShow.getTheTVDbName()
                                            .equals(fullSeriesRecord.getBaseSeriesRecord().getSeriesName())) {
                                        tvShow.setTheTVDbName(fullSeriesRecord.getBaseSeriesRecord().getSeriesName());

                                        changed = Boolean.TRUE;
                                    }


                                    if (changed) {
                                        tvShowRepository.save(
                                                tvShow
                                        );
                                    }

                                    Map<Season, Map<Integer, Episode>> episodeMap = new HashMap<>();
                                    Map<Integer, Season> seasonMap = new HashMap<>();

                                    if (fullSeriesRecord.getBaseEpisodeRecords() != null) {
                                        for (BaseEpisodeRecord baseEpisodeRecord : fullSeriesRecord.getBaseEpisodeRecords()) {
                                            if (baseEpisodeRecord.getEpisodeName() != null) {
                                                Season season;

                                                if (!seasonMap.containsKey(baseEpisodeRecord.getSeasonNumber())) {
                                                    season = seasonRepository.findByTvShowAndTheTVDbSeasonNumber(
                                                            tvShow,
                                                            baseEpisodeRecord.getSeasonNumber()
                                                    );

                                                    if (season == null) {
                                                        season = new Season();

                                                        season.setTvShow(
                                                                tvShow
                                                        );

                                                        season.setTheTVDbSeasonNumber(
                                                                baseEpisodeRecord.getSeasonNumber()
                                                        );

                                                        season.setTheTVDbSeasonId(
                                                                baseEpisodeRecord.getSeasonId()
                                                        );

                                                        seasonRepository.save(
                                                                season
                                                        );
                                                    }

                                                    seasonMap.put(
                                                            baseEpisodeRecord.getSeasonNumber(),
                                                            season
                                                    );

                                                    episodeMap.put(season, new HashMap<Integer, Episode>());
                                                } else {
                                                    season = seasonMap.get(baseEpisodeRecord.getSeasonNumber());
                                                }

                                                Episode episode = episodeRepository.findBySeasonAndTheTVDbEpisodeName(
                                                        season,
                                                        baseEpisodeRecord.getEpisodeName()
                                                );

                                                if (episode == null) {
                                                    episode = new Episode();

                                                    episode.setSeason(
                                                            season
                                                    );

                                                    episode.setTheTVDbEpisodeName(
                                                            baseEpisodeRecord.getEpisodeName()
                                                    );

                                                    episodeRepository.save(
                                                            episode
                                                    );
                                                }


                                                episodeMap.get(
                                                        season
                                                ).put(
                                                        baseEpisodeRecord.getEpisodeNumber(),
                                                        episode
                                                );
                                            }
                                        }
                                    }


                                    DirectoryList seasonsDirectoryList = plexConnector.getMetaData(
                                            server,
                                            tvShowDirectory.getKey()
                                    );

                                    if (seasonsDirectoryList.getDirectories() != null) {
                                        for (Directory seasonDirectory : seasonsDirectoryList.getDirectories()) {
                                            if (seasonDirectory.getIndex() != null) {
                                                if (seasonMap.containsKey(seasonDirectory.getIndex())) {
                                                    Season season = seasonMap.get(
                                                            seasonDirectory.getIndex()
                                                    );

                                                    Map<Integer, Episode> seasonEpisodes = episodeMap.get(
                                                            season
                                                    );

                                                    changed = Boolean.FALSE;

                                                    if (season.getPlexKey() == null || !season.getPlexKey().equals(seasonDirectory.getKey())) {
                                                        season.setPlexKey(
                                                                seasonDirectory.getKey()
                                                        );

                                                        changed = Boolean.TRUE;
                                                    }

                                                    if (season.getPlexName() == null || !season.getPlexName().equals(seasonDirectory.getTitle())) {
                                                        season.setPlexName(
                                                                seasonDirectory.getTitle()
                                                        );

                                                        changed = Boolean.TRUE;
                                                    }

                                                    if (changed) {
                                                        seasonRepository.save(
                                                                season
                                                        );
                                                    }

                                                    DirectoryList episodesDirectoryList = plexConnector.getMetaData(
                                                            server,
                                                            seasonDirectory.getKey()
                                                    );

                                                    if (episodesDirectoryList.getVideos() != null) {
                                                        for (Video episodeVideo : episodesDirectoryList.getVideos()) {
                                                            if (seasonEpisodes.containsKey(episodeVideo.getIndex())) {
                                                                Episode episode = seasonEpisodes.get(
                                                                        episodeVideo.getIndex()
                                                                );

                                                                changed = Boolean.FALSE;

                                                                if (episode.getPlexKey() == null || !episode.getPlexKey().equals(episodeVideo.getKey())) {
                                                                    episode.setPlexKey(
                                                                            episodeVideo.getKey()
                                                                    );

                                                                    changed = Boolean.TRUE;
                                                                }

                                                                if (episode.getPlexName() == null || !episode.getPlexName().equals(episodeVideo.getTitle())) {
                                                                    episode.setPlexName(
                                                                            episodeVideo.getTitle()
                                                                    );

                                                                    changed = Boolean.TRUE;
                                                                }

                                                                if (changed) {
                                                                    episodeRepository.save(
                                                                            episode
                                                                    );
                                                                }
                                                            } else {
                                                                log.warn(
                                                                        String.format(
                                                                                "Episode \"%s\" was not found on TheTVDb",
                                                                                episodeVideo.getTitle()
                                                                        )
                                                                );
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    throw new GapCrawlerException(
                                                            String.format(
                                                                    "Season %s was not found on TheTVDb",
                                                                    seasonDirectory.getIndex()
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
                }
            }
        }
    }
}
