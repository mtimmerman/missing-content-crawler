package com.mtimmerman.service;

import com.mtimmerman.Application;
import com.mtimmerman.resources.lastfm.AlbumList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class LastFMConnectorTest {
    private LastFMConnector lastFMConnector;
    @Autowired
    protected ConfigurableEnvironment configurableEnvironment;

    @Before
    public void setUp() throws Exception {
        lastFMConnector = new LastFMConnector();

        String apiKey = configurableEnvironment.getRequiredProperty(
                "lastFM.apiKey"
        );

        lastFMConnector.setApiKey(
                apiKey
        );

        lastFMConnector.setBaseUrl(
                configurableEnvironment.getRequiredProperty(
                        "lastFM.baseUrl"
                )
        );
    }

    @Test
    public void testGetArtistGetTopAlbums() throws Exception {
        AlbumList albumList = lastFMConnector.getArtistGetTopAlbums(
                "Cher",
                null,
                true,
                0,
                50
        );

        Assert.assertEquals(50, albumList.getLastFMAlbums().length);
        Assert.assertTrue(albumList.getTotalPages() > 1);
    }
}