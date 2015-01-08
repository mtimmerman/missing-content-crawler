package com.mtimmerman.service;


import com.mtimmerman.Application;
import com.mtimmerman.domain.plex.DeviceList;
import com.mtimmerman.domain.plex.Directory;
import com.mtimmerman.domain.plex.DirectoryList;
import com.mtimmerman.domain.plex.OnDeckList;
import com.mtimmerman.domain.plex.PlayList;
import com.mtimmerman.domain.plex.Server;
import com.mtimmerman.domain.plex.ServerList;
import com.mtimmerman.domain.plex.User;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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
public class PlexConnectorTest {
    private PlexConnector plexConnector;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Before
    public void setUp() throws Exception {
        plexConnector = new PlexConnector();

        String username = configurableEnvironment.getRequiredProperty(
                "plex.username"
        );

        String password = configurableEnvironment.getRequiredProperty(
                "plex.password"
        );

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(
                        username,
                        password
                )
        );

        plexConnector.setCredentialsProvider(
                credentialsProvider
        );
    }

    @Test
    public void testLogin() throws Exception {
        Boolean loggedIn = plexConnector.logIn();

        Assert.assertTrue(loggedIn);
    }

    public ServerList testGetServers() throws Exception {
        ServerList serverList = plexConnector.getServers();

        Assert.assertTrue(serverList.getServers().length == serverList.getSize());

        return serverList;
    }

    @Test
    public void testGetServer() throws Exception {
        String serverName = configurableEnvironment.getRequiredProperty("plex.server");

        Server server = plexConnector.getServer(serverName);

        Assert.assertEquals(
                serverName,
                server.getName()
        );
    }

    @Test
    public void testGetDevices() throws Exception {
        DeviceList deviceList = plexConnector.getDevices();

        Assert.assertTrue(deviceList.getDevices().length > 0);
    }

    private void drillDownPlayList(PlayList parentPlayList) throws Exception {
        System.out.println(parentPlayList.getUriUsed().toString());
        if (parentPlayList.getDirectories() != null) {
            for (Directory directory : parentPlayList.getDirectories()) {
                PlayList playList = plexConnector.getPlayLists(parentPlayList, directory.getKey());

                drillDownPlayList(playList);
            }
        }
    }

    @Test
    public void testGetPlayLists() throws Exception {
        PlayList playList = plexConnector.getPlayLists(null, null);

        Assert.assertTrue(playList.getDirectories().length > 0);

        drillDownPlayList(playList);
    }

    @Test
    public void testGetUserAccount() throws Exception
    {
        User user = plexConnector.getUserAccount();

        Assert.assertNotNull(user);
        Assert.assertTrue(
                user.getAuthenticationToken() != null &&
                !user.getAuthenticationToken().isEmpty() &&
                user.getAuthenticationToken().equals(user.getAuthenticationToken2())
        );
    }

    private void drillDownSection(Server server, DirectoryList parentDirectoryList) throws Exception {
        System.out.println(parentDirectoryList.getUriUsed().toString());
        if (parentDirectoryList.getDirectories() != null) {
            for (Directory directory : parentDirectoryList.getDirectories()) {
                if (directory.getKey().equals("1") || directory.getKey().equals("2") || directory.getKey().equals("5") || directory.getKey().equals("3"))
                    continue;

                DirectoryList directoryList = plexConnector.getSections(server, parentDirectoryList, directory.getKey());

                drillDownSection(server, directoryList);
            }
        }
    }

    @Test
    public void testGetSections() throws Exception {
        ServerList serverList = testGetServers();

        if (serverList.getSize() > 0) {
            DirectoryList directoryList = plexConnector.getSections(serverList.getServers()[0], null, null);

            Assert.assertTrue(directoryList.getDirectories().length == directoryList.getSize());

            drillDownSection(serverList.getServers()[0], directoryList);
        }
    }

    @Test
    public void testGetOnDeck() throws Exception {
        ServerList serverList = testGetServers();

        if (serverList.getSize() > 0) {
            OnDeckList onDeckList = plexConnector.getOnDeck(serverList.getServers()[0]);

            Assert.assertTrue(onDeckList.getVideos().length == onDeckList.getSize());
        }
    }
}