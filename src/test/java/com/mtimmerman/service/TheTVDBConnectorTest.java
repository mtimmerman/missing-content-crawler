package com.mtimmerman.service;

import com.mtimmerman.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TheTVDBConnectorTest {
    @Autowired
    private TheTVDBConnector theTVDBConnector;

    @Test
    public void testGetSeries() throws Exception {
        theTVDBConnector.getSeries(
                "The Big Bang Theory"
        );
    }
}