package com.mtimmerman.service;

import com.mtimmerman.domain.lastfm.AlbumList;
import com.mtimmerman.domain.lastfm.LastFMContainer;
import com.mtimmerman.domain.lastfm.enums.LastFMStatus;
import com.mtimmerman.service.exceptions.HttpException;
import com.mtimmerman.service.exceptions.LastFMException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by maarten on 29.12.14.
 */
public class LastFMConnector extends AbstractConnector {
    private String apiKey;
    private HttpClient httpClient;
    private String baseUrl;

    private static final Logger log = LoggerFactory.getLogger(
            LastFMConnector.class
    );

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    protected HttpGet createGET(String uri) {
        log.info(
                String.format(
                        "GET --> %s",
                        uri
                )
        );

        return new HttpGet(uri);
    }

    @Override
    HttpPost createPOST(String uri) {
        return null;
    }

    public AlbumList getArtistGetTopAlbums(String artist, String mbid, Boolean autoCorrect, Integer page, Integer limit)
        throws IOException, LastFMException {
        String uri = String.format(
                "%s/2.0/?method=artist.gettopalbums&artist=%s&mbid=%s&autocorrect=%s&page=%s&limit=%s&api_key=%s",
                baseUrl,
                urlEncodeUTF8(
                        artist
                ),
                urlEncodeUTF8(
                        mbid
                ),
                autoCorrect ? "1" : "0",
                page,
                limit,
                urlEncodeUTF8(
                        apiKey
                )
        );

        Boolean retry = Boolean.TRUE;
        Integer retryCount = 0;

        while (retry && retryCount < 3) {
            HttpGet httpGet = createGET(
                    uri
            );

            try {
                HttpResponse httpResponse = handleResponse(
                        httpGet
                );

                String result = EntityUtils.toString(
                        httpResponse.getEntity()
                );

                LastFMContainer lastFMContainer = xmlMapper.readValue(
                        result,
                        LastFMContainer.class
                );

                if (lastFMContainer.getLastFMStatus() == LastFMStatus.failed) {
                    throw new LastFMException(
                            lastFMContainer.getError().getMessage(),
                            lastFMContainer.getError().getCode()
                    );
                }

                return lastFMContainer.getTopAlbums();
            } catch (HttpException e) {
                retry = e.getCode() == 522;
                if (!retry) {
                    throw e;
                } else {
                    retryCount++;
                }
            }
        }

        return null;
    }
}
