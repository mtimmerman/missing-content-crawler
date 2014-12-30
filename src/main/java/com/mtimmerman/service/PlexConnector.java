package com.mtimmerman.service;

import com.mtimmerman.rest.resources.plex.DeviceList;
import com.mtimmerman.rest.resources.plex.DirectoryList;
import com.mtimmerman.rest.resources.plex.OnDeckList;
import com.mtimmerman.rest.resources.plex.PlayList;
import com.mtimmerman.rest.resources.plex.Server;
import com.mtimmerman.rest.resources.plex.ServerList;
import com.mtimmerman.rest.resources.plex.User;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by maarten on 24.12.14.
 */
public class PlexConnector extends AbstractConnector {
    private User loggedInUser;

    private static final Logger log = LoggerFactory.getLogger(
            PlexConnector.class
    );

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        httpClientContext = HttpClientContext.create();
        httpClientContext.setCredentialsProvider(
                credentialsProvider
        );
    }

    @Override
    protected HttpGet createGET(String uri) {
        log.info(
                String.format(
                        "GET --> %s",
                        uri
                )
        );

        HttpGet httpGet = new HttpGet(
                uri
        );

        httpGet.addHeader(
                "X-Plex-Version",
                "2.3.7"
        );
        httpGet.addHeader(
                "X-Plex-Platform-Version",
                "39.0");
        httpGet.addHeader(
                "X-Plex-Client-Identifier",
                "34a230a9-9a05-4b50-80a0-c38f4ec905e6"
        );
        httpGet.addHeader(
                "X-Plex-Device-Name",
                "Plex Web (Chrome)"
        );
        httpGet.addHeader(
                "X-Plex-Platform",
                "Chrome"
        );
        httpGet.addHeader(
                "X-Plex-Product",
                "Plex Web"
        );
        httpGet.addHeader(
                "X-Plex-Device",
                "Linux"
        );

        return httpGet;
    }

    @Override
    protected HttpPost createPOST(String uri) {
        log.info(
                String.format(
                        "POST --> %s",
                        uri
                )
        );

        HttpPost httpPost = new HttpPost(
                uri
        );

        httpPost.addHeader(
                "X-Plex-Version",
                "2.3.7"
        );
        httpPost.addHeader(
                "X-Plex-Platform-Version",
                "39.0"
        );
        httpPost.addHeader(
                "X-Plex-Client-Identifier",
                "34a230a9-9a05-4b50-80a0-c38f4ec905e6"
        );
        httpPost.addHeader(
                "X-Plex-Device-Name",
                "Plex Web (Chrome)"
        );
        httpPost.addHeader(
                "X-Plex-Platform",
                "Chrome"
        );
        httpPost.addHeader(
                "X-Plex-Product",
                "Plex Web"
        );
        httpPost.addHeader(
                "X-Plex-Device",
                "Linux"
        );

        return httpPost;
    }

    private void addPlexToken(HttpGet httpGet) throws IOException{
        if (loggedInUser != null || logIn()) {
            httpGet.addHeader(
                    "X-Plex-Token",
                    loggedInUser.getAuthenticationToken()
            );
        }
    }

    public Boolean logIn() throws IOException {
        HttpPost httpPost = createPOST(
                "https://plex.tv/users/sign_in.xml"
        );

        HttpResponse httpResponse = handleResponse(
                httpPost
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        loggedInUser = xmlMapper.readValue(
                result,
                User.class
        );
        loggedInUser.setUriUsed(
                httpPost.getURI()
        );

        return Boolean.TRUE;
    }

    public ServerList getServers() throws IOException {
        HttpGet httpGet = new HttpGet(
                "https://plex.tv/pms/servers.xml"
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        ServerList serverList = xmlMapper.readValue(
                result,
                ServerList.class
        );
        serverList.setUriUsed(
                httpGet.getURI()
        );

        return serverList;
    }

    public Server getServer(String name) throws IOException, PlexServerNotFoundException {
        ServerList serverList = getServers();

        for (Server server: serverList.getServers()) {
            if (server.getName().equals(name)) {
                return server;
            }
        }

        throw new PlexServerNotFoundException(
                String.format(
                        "Server \"%s\" was not found.",
                        name
                )
        );
    }

    public DeviceList getDevices() throws IOException {
        HttpGet httpGet = new HttpGet(
                "https://plex.tv/devices.xml"
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        DeviceList deviceList = xmlMapper.readValue(
                result,
                DeviceList.class
        );
        deviceList.setUriUsed(
                httpGet.getURI()
        );

        return deviceList;
    }

    public User getUserAccount() throws IOException {
        HttpGet httpGet = new HttpGet(
                "https://plex.tv/users/account"
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        User user = xmlMapper.readValue(
                result,
                User.class
        );
        user.setUriUsed(
                httpGet.getURI()
        );

        return user;
    }

    public DirectoryList getMetaData(Server server, String key) throws IOException{
        String uri = String.format(
                "%s%s",
                server.getServerEndpoint(),
                key
        );

        HttpGet httpGet = createGET(
                uri
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        DirectoryList directoryList = xmlMapper.readValue(
                result,
                DirectoryList.class
        );
        directoryList.setUriUsed(
                httpGet.getURI()
        );
        return directoryList;
    }

    public DirectoryList getSections(Server server, DirectoryList parentDirectoryList, String key) throws IOException {
        String uri =String.format(
                "%s/library/sections",
                server.getServerEndpoint()
        );

        if (parentDirectoryList != null) {
            if (key.startsWith("/")) {
                return getMetaData(
                        server,
                        key
                );
            } else {
                uri = String.format(
                        "%s/%s",
                        parentDirectoryList.getUriUsed().toString(),
                        key
                );
            }
        }

        HttpGet httpGet = createGET(
                uri
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        DirectoryList directoryList = xmlMapper.readValue(
                result,
                DirectoryList.class
        );
        directoryList.setUriUsed(
                httpGet.getURI()
        );
        return directoryList;
    }

    public PlayList getPlayLists(PlayList parentPlayList, String key) throws IOException {
        String uri = "https://plex.tv/pms/playlists";

        if (parentPlayList != null) {
            uri = String.format(
                    "%s/%s",
                    parentPlayList.getUriUsed().toString(),
                    key
            );
        }

        HttpGet httpGet = createGET(
                uri
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        PlayList playList = xmlMapper.readValue(
                result,
                PlayList.class
        );
        playList.setUriUsed(
                httpGet.getURI()
        );

        return playList;
    }

    public OnDeckList getOnDeck(Server server) throws IOException {
        String uri =String.format(
                "%s/library/onDeck",
                server.getServerEndpoint()
        );

        HttpGet httpGet = createGET(
                uri
        );
        addPlexToken(
                httpGet
        );

        HttpResponse httpResponse = handleResponse(
                httpGet
        );

        String result = EntityUtils.toString(
                httpResponse.getEntity()
        );

        OnDeckList onDeckList = xmlMapper.readValue(
                result,
                OnDeckList.class
        );
        onDeckList.setUriUsed(
                httpGet.getURI()
        );

        return onDeckList;
    }
}
