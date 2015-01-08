package com.mtimmerman.controllers.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.repositories.SeasonRepository;
import com.mtimmerman.repositories.TvShowRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by maarten on 08.01.15.
 */
public abstract class AbstractTvEpisodeControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    protected TvShowRepository tvShowRepository;
    @Autowired
    protected SeasonRepository seasonRepository;
    @Autowired
    protected EpisodeRepository episodeRepository;

    private MockMvc mockMvc;
    protected Gson gson;

    protected TvShow tvShow;
    protected Season season;
    protected Episode downloadedEpisode;
    protected Episode missingEpisode;
    protected Episode notYetAiredEpisode;
    protected Episode unknownAiredEpisode;

    private TvShow createTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.setEpisodesMissing(
                1
        );
        tvShow.setPlexKey(
                "test-key"
        );
        tvShow.setPlexName(
                "test"
        );
        tvShow.setTheTVDbName(
                "test"
        );

        return tvShowRepository.save(
                tvShow
        );
    }

    private Season createSeason(TvShow tvShow) {
        Season season = new Season();

        season.setEpisodesMissing(
                1
        );

        season.setPlexKey(
                "season1-key"
        );

        season.setPlexName(
                "season1"
        );

        season.setTheTVDbSeasonId(
                1
        );

        season.setTheTVDbSeasonNumber(
                1
        );

        season.setTvShow(
                tvShow
        );

        return seasonRepository.save(
                season
        );
    }

    private Episode createEpisode(
            Date firstAiredOn,
            String plexKey,
            String plexName,
            String searchName,
            Season season,
            String theTVDbEpisodeName,
            Integer theTVDbEpisodeNumber
    ) {
        Episode episode = new Episode();

        episode.setFirstAiredOn(
                firstAiredOn
        );

        episode.setPlexKey(
                plexKey
        );

        episode.setPlexName(
                plexName
        );

        episode.setSearchName(
                searchName
        );

        episode.setSeason(
                season
        );

        episode.setTheTVDbEpisodeName(
                theTVDbEpisodeName
        );

        episode.setTheTVDbEpisodeNumber(
                theTVDbEpisodeNumber
        );

        return episodeRepository.save(episode);
    }

    protected String doGET(String uri, HttpStatus expectedStatus) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(
                uri
        );

        ResultActions resultActions = mockMvc
                .perform(mockHttpServletRequestBuilder);

        MvcResult mvcResult = resultActions.andReturn();
        Exception exception = mvcResult.getResolvedException();

        if (exception != null) {
            throw exception;
        }

        resultActions.andExpect(status().is(expectedStatus.value()));

        return mvcResult.getResponse().getContentAsString();
    }

    @Before
    public void setUp() throws Exception {
        gson = new GsonBuilder()
                .setDateFormat(
                        "yyyy-MM-dd"
                )
                .create();

        mockMvc = MockMvcBuilders.webAppContextSetup(
                webApplicationContext
        ).build();

        tvShow = createTvShow();

        season = createSeason(tvShow);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(
                new Date()
        );
        calendar.add(Calendar.DATE, -1);


        downloadedEpisode = createEpisode(
                calendar.getTime(),
                "ep1-key",
                "ep1",
                "search",
                season,
                "ep1",
                1
        );

        missingEpisode = createEpisode(
                calendar.getTime(),
                null,
                null,
                "search",
                season,
                "ep1",
                1
        );

        calendar.add(Calendar.DATE, 10);

        notYetAiredEpisode = createEpisode(
                calendar.getTime(),
                null,
                null,
                "search",
                season,
                "ep1",
                1
        );

        unknownAiredEpisode = createEpisode(
                null,
                "ep2-key",
                "ep2",
                "search",
                season,
                "ep2",
                2
        );
    }

    @After
    public void tearDown() throws Exception {
        episodeRepository.deleteAll();
        seasonRepository.deleteAll();
        tvShowRepository.deleteAll();
    }
}
