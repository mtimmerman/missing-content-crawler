package com.mtimmerman.service;

import com.mtimmerman.rest.resources.lastfm.AlbumList;
import com.mtimmerman.rest.resources.lastfm.LastFMContainer;
import com.mtimmerman.rest.resources.lastfm.enums.LastFMStatus;
import com.mtimmerman.service.exceptions.LastFMException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by maarten on 29.12.14.
 */
public class LastFMConnector extends AbstractConnector {
    private String apiKey;
    private String apiSecret;
    private HttpClient httpClient;
    private String baseUrl;

    public LastFMConnector() {
        httpClient = HttpClientBuilder.create().build();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    protected HttpGet createGET(String uri) {
        return new HttpGet(uri);
    }

    @Override
    HttpPost createPOST(String uri) {
        return null;
    }

    private String urlEncodeUTF8(String s) {
        if (s != null) {
            try {
                return URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        return "";
    }

    public AlbumList getArtistGetTopAlbums(String artist, String mbid, Boolean autoCorrect, Integer page, Integer limit)
        throws IOException, LastFMException {
        String uri = String.format(
                "%s/2.0/?method=artist.gettopalbums&artist=%s&mbid=%s&autocorrect=%s&page=%s&limit=%s&api_key=%s",
                baseUrl,
                urlEncodeUTF8(artist),
                urlEncodeUTF8(mbid),
                autoCorrect ? "1" : "0",
                page,
                limit,
                urlEncodeUTF8(apiKey)
        );

        HttpGet httpGet = createGET(uri);

        HttpResponse httpResponse = handleResponse(httpGet);

        String result = EntityUtils.toString(httpResponse.getEntity());

        LastFMContainer lastFMContainer = xmlMapper.readValue(result, LastFMContainer.class);

        if (lastFMContainer.getLastFMStatus() == LastFMStatus.failed) {
            throw new LastFMException(
                    lastFMContainer.getError().getMessage(),
                    lastFMContainer.getError().getCode()
            );
        }

        return lastFMContainer.getTopAlbums();
    }
}
