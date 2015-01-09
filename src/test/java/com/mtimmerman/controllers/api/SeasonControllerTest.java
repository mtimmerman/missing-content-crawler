package com.mtimmerman.controllers.api;

import com.google.gson.reflect.TypeToken;
import com.mtimmerman.Application;
import com.mtimmerman.resources.EpisodeResource;
import com.mtimmerman.resources.SeasonResource;
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
public class SeasonControllerTest extends AbstractTvEpisodeControllerTest {

    @Test
    public void testList() throws Exception {
        String content = doGET(
                "/api/seasons",
                HttpStatus.OK
        );

        Type type = new TypeToken<List<SeasonResource>>() {
        }.getType();
        List<SeasonResource> seasonResources = gson.fromJson(content, type);
        Assert.assertEquals(1, seasonResources.size());
        Assert.assertEquals(season.getId(), seasonResources.get(0).getPk());
    }

    @Test
    public void testDetail() throws Exception {
        String content = doGET(
                String.format(
                        "/api/seasons/%s",
                        season.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<SeasonResource>() {
        }.getType();
        SeasonResource seasonResource = gson.fromJson(content, type);
        Assert.assertEquals(season.getId(), seasonResource.getPk());
    }

    @Test
    public void testGetEpisodesForSeason() throws Exception {
        String content = doGET(
                String.format(
                        "/api/seasons/%s/episodes",
                        season.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(3, episodeResources.size());
    }

    @Test
    public void testGetEpisodesForSeasonNotOnPlex() throws Exception {
        String content = doGET(
                String.format(
                        "/api/seasons/%s/episodes-not-on-plex",
                        season.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(0, episodeResources.size());
    }
}