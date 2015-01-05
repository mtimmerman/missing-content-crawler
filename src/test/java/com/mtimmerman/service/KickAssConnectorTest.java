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
public class KickAssConnectorTest {
    @Autowired
    private KickAssConnector kickAssConnector;

    @Test
    public void testRunHourlyUpdate() throws Exception {
        kickAssConnector.runHourlyUpdate(null);
    }

    @Test
    public void testRunCompleteUpdate() throws Exception {
        kickAssConnector.runCompleteUpdate("/home/maarten/dailydump.txt.gz");
    }
}