package com.mtimmerman.controllers.api;

import com.google.gson.reflect.TypeToken;
import com.mtimmerman.Application;
import com.mtimmerman.resources.EpisodeResource;
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
public class EpisodeControllerTest extends AbstractTvEpisodeControllerTest {

    @Test
    public void testList() throws Exception {
        String content = doGET(
                "/api/episodes",
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(4, episodeResources.size());
    }

    @Test
    public void testDetail() throws Exception {
        String content = doGET(
                String.format(
                        "/api/episodes/%s",
                        downloadedEpisode.getId()
                ),
                HttpStatus.OK
        );

        Type type = new TypeToken<EpisodeResource>() {
        }.getType();
        EpisodeResource episodeResource = gson.fromJson(content, type);
        Assert.assertEquals(downloadedEpisode.getId(), episodeResource.getPk());
    }

    @Test
    public void testListNotOnPlex() throws Exception {
        String content = doGET(
                "/api/episodes/not-on-plex",
                HttpStatus.OK
        );

        Type type = new TypeToken<List<EpisodeResource>>() {
        }.getType();
        List<EpisodeResource> episodeResources = gson.fromJson(content, type);
        Assert.assertEquals(1, episodeResources.size());
    }
}