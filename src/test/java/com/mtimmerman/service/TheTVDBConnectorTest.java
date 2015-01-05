package com.mtimmerman.service;

import com.mtimmerman.Application;
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
public class TheTVDBConnectorTest {
    private TheTVDBConnector theTVDBConnector;
    @Autowired
    protected ConfigurableEnvironment configurableEnvironment;

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void testGetSeries() throws Exception {
        theTVDBConnector.getSeries(
                "The Big Bang Theory"
        );
    }
}