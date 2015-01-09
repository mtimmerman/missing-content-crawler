package com.mtimmerman.controllers.api;

import com.google.gson.reflect.TypeToken;
import com.mtimmerman.Application;
import com.mtimmerman.resources.EpisodeResource;
import com.mtimmerman.resources.SeasonResource;
import com.mtimmerman.resources.TvShowResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Type;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TvShowControllerTest extends AbstractTvEpisodeControllerTest {
    @Test
    public void testList() throws Exception {
        String content = doGET(
                "/api/tvshows",
                HttpStatus.OK
        );

        Type type = new TypeToken<List<TvShowResource>>() {
        }.getType();
        List<TvShowResource> tvShowResources = gson.fromJson(content, type);
        Assert.assertEquals(1, tvShowResources.size());
        Assert.assertEquals(tvShow.getId(), tvShowResources.get(0).getPk());
    }

    @Test
    public void testDetail() throws Exception {
        String content = doGET(
                String.format(
                        "/api/tvshows/%s",
                        tvShow.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<TvShowResource>() {
        }.getType();
        TvShowResource tvShowResource = gson.fromJson(content, type);
        Assert.assertEquals(tvShow.getId(), tvShowResource.getPk());
    }

    @Test
    public void testGetSeasonsForTvShow() throws Exception {
        String content = doGET(
                String.format(
                        "/api/tvshows/%s/seasons",
                        tvShow.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<List<SeasonResource>>() {
        }.getType();
        List<SeasonResource> seasonResources = gson.fromJson(content, type);
        Assert.assertEquals(season.getId(), seasonResources.get(0).getPk());
    }

    @Test
    public void testGetEpisodesForTvShow() throws Exception {
        String content = doGET(
                String.format(
                        "/api/tvshows/%s/episodes",
                        tvShow.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(4, episodeResources.size());
    }

    @Test
    public void testGetEpisodesForTvShowNotOnPlex() throws Exception {
        String content = doGET(
                String.format(
                        "/api/tvshows/%s/episodes-not-on-plex",
                        tvShow.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(1, episodeResources.size());
    }
}