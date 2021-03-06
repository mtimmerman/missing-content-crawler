package com.mtimmerman.service;

import com.mtimmerman.domain.plex.Directory;
import com.mtimmerman.domain.plex.DirectoryList;
import com.mtimmerman.domain.plex.Server;
import com.mtimmerman.domain.plex.Video;
import com.mtimmerman.domain.plex.enums.DirectoryType;
import com.mtimmerman.domain.thetvdb.BaseEpisodeRecord;
import com.mtimmerman.domain.thetvdb.FullSeriesRecord;
import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.repositories.SeasonRepository;
import com.mtimmerman.repositories.TvShowRepository;
import com.mtimmerman.service.exceptions.GapCrawlerException;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maarten on 05.01.15.
 */
@Component
@Scope("prototype")
public class TvEpisodeCrawler extends AbstractCrawler {
    private TheTVDBConnector theTVDBConnector;
    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    private Map<Season, Map<Integer, Episode>> episodeMap = new HashMap<>();
    private Map<Integer, Season> seasonMap = new HashMap<>();

    private TheTVDBConnector getTheTVDBConnector() {
        if (theTVDBConnector == null) {
            theTVDBConnector = new TheTVDBConnector();

            String apiKey = configurableEnvironment.getRequiredProperty(
                    "theTVDB.apiKey"
            );

            theTVDBConnector.setApiKey(
                    apiKey
            );

            theTVDBConnector.setBaseUrl(
                    configurableEnvironment.getRequiredProperty(
                            "theTVDB.baseUrl"
                    )
            );
        }

        return theTVDBConnector;
    }

    private TvShow getTvShow(
            Directory tvShowDirectory,
            FullSeriesRecord fullSeriesRecord
    ) {
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

        return tvShow;
    }

    private Season getSeason(
            BaseEpisodeRecord baseEpisodeRecord,
            TvShow tvShow
    ) {
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

        return season;
    }

    private void processEpisode(
            Season season,
            BaseEpisodeRecord baseEpisodeRecord
    ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");

        Episode episode = episodeRepository.findBySeasonAndTheTVDbEpisodeName(
                season,
                baseEpisodeRecord.getEpisodeName()
        );

        Boolean changed = Boolean.FALSE;

        if (episode == null) {
            episode = new Episode();

            episode.setSeason(
                    season
            );

            episode.setTheTVDbEpisodeName(
                    baseEpisodeRecord.getEpisodeName()
            );

            episode.setTheTVDbEpisodeNumber(
                    baseEpisodeRecord.getEpisodeNumber()
            );

            episode.setSearchName(
                    String.format(
                            "%s S%sE%s",
                            season.getTvShow().getTheTVDbName(),
                            String.format(
                                    "%02d",
                                    season.getTheTVDbSeasonNumber()
                            ),
                            String.format(
                                    "%02d",
                                    baseEpisodeRecord.getEpisodeNumber()
                            )
                    )
            );

            changed = Boolean.TRUE;
        }

        if (baseEpisodeRecord.getFirstAired() != null) {
            Date firstAired = simpleDateFormat.parse(
                    baseEpisodeRecord.getFirstAired()
            );

            if (episode.getFirstAiredOn() == null || episode.getFirstAiredOn().compareTo(firstAired) != 0) {
                episode.setFirstAiredOn(
                        firstAired
                );

                changed = Boolean.TRUE;
            }
        }

        if (changed) {
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

    private TvShow findOnTheTVDb(Directory tvShowDirectory)
            throws IOException,
            TheTVDBConnectorException,
            ParseException {
        logInfo(
                String.format(
                        "TV SHOW: %s",
                        tvShowDirectory.getTitle()
                )
        );

        FullSeriesRecord fullSeriesRecord = getTheTVDBConnector().getSeries(
                tvShowDirectory.getTitle()
        );

        if (fullSeriesRecord != null) {
            TvShow tvShow = getTvShow(
                    tvShowDirectory,
                    fullSeriesRecord
            );

            episodeMap = new HashMap<>();
            seasonMap = new HashMap<>();

            if (fullSeriesRecord.getBaseEpisodeRecords() != null) {
                for (BaseEpisodeRecord baseEpisodeRecord : fullSeriesRecord.getBaseEpisodeRecords()) {
                    if (baseEpisodeRecord.getEpisodeName() != null) {
                        Season season = getSeason(
                                baseEpisodeRecord,
                                tvShow
                        );

                        processEpisode(
                                season,
                                baseEpisodeRecord
                        );
                    }
                }
            }

            return tvShow;
        }

        return null;
    }

    private void updateEpisode(Map<Integer, Episode> seasonEpisodes, Video episodeVideo) {
        Episode episode = seasonEpisodes.get(
                episodeVideo.getIndex()
        );

        Boolean changed = Boolean.FALSE;

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
    }

    private void updateSeason(Season season, Directory seasonDirectory) {
        Boolean changed = Boolean.FALSE;

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
    }

    private void findOnPlex(Server server, Directory tvShowDirectory)
            throws IOException, GapCrawlerException {
        DirectoryList seasonsDirectoryList = getPlexConnector().getMetaData(
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

                        updateSeason(
                                season,
                                seasonDirectory
                        );

                        DirectoryList episodesDirectoryList = getPlexConnector().getMetaData(
                                server,
                                seasonDirectory.getKey()
                        );

                        if (episodesDirectoryList.getVideos() != null) {
                            for (Video episodeVideo : episodesDirectoryList.getVideos()) {
                                if (seasonEpisodes.containsKey(episodeVideo.getIndex())) {
                                    updateEpisode(
                                            seasonEpisodes,
                                            episodeVideo
                                    );
                                } else {
                                    logWarn(
                                            String.format(
                                                    "Episode \"%s\" was not found on TheTVDb",
                                                    episodeVideo.getTitle()
                                            )
                                    );
                                }
                            }
                        }

                        Integer count = episodeRepository.countBySeasonNotOnPlex(
                                season
                        );

                        if (count == null) {
                            count = 0;
                        }

                        if (season.getEpisodesMissing() == null || !season.getEpisodesMissing().equals(count)) {
                            season.setEpisodesMissing(
                                    count
                            );

                            seasonRepository.save(
                                    season
                            );
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

    @Override
    void process() throws
            PlexServerNotFoundException,
            IOException,
            TheTVDBConnectorException,
            GapCrawlerException,
            ParseException {
        Server server = getPlexConnector().getServer(
                getServerName()
        );

        DirectoryList root = getPlexConnector().getSections(
                server,
                null,
                null
        );

        for (Directory rootDirectory : root.getDirectories()) {
            if (rootDirectory.getType() == DirectoryType.show) {
                DirectoryList showDirectoryList = getPlexConnector().getSections(
                        server,
                        root,
                        rootDirectory.getKey()
                );

                for (Directory showDirectory : showDirectoryList.getDirectories()) {
                    if (showDirectory.getKey().equals("all")) {
                        DirectoryList allTVShowsDirectoryList = getPlexConnector().getSections(
                                server,
                                showDirectoryList,
                                showDirectory.getKey()
                        );

                        for (Directory tvShowDirectory : allTVShowsDirectoryList.getDirectories()) {
                            if (tvShowDirectory.getType() == DirectoryType.show) {
                                TvShow tvShow = findOnTheTVDb(tvShowDirectory);
                                if (tvShow != null) {
                                    findOnPlex(
                                            server,
                                            tvShowDirectory);

                                    Integer count = episodeRepository.countByTvShowNotOnPlex(
                                            tvShow
                                    );

                                    if (count == null) {
                                        count = 0;
                                    }

                                    if (tvShow.getEpisodesMissing() == null || !tvShow.getEpisodesMissing().equals(count)) {
                                        tvShow.setEpisodesMissing(
                                                count
                                        );

                                        tvShowRepository.save(
                                                tvShow
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