package com.mtimmerman.service;

import com.mtimmerman.rest.resources.thetvdb.SeriesList;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by maarten on 29.12.14.
 */
public class TheTVDBConnector extends AbstractConnector {
    private String apiKey;
    private String baseUrl;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    HttpGet createGET(String uri) {
        return new HttpGet(uri);
    }

    @Override
    HttpPost createPOST(String uri) {
        return null;
    }

    public SeriesList getSeries(String seriesName)
            throws IOException, TheTVDBConnectorException {
        String uri = String.format(
                "%s/api/GetSeries.php?seriesname=%s",
                baseUrl,
                urlEncodeUTF8(seriesName)
        );

        HttpGet httpGet = createGET(uri);

        HttpResponse httpResponse = handleResponse(httpGet);

        String result = EntityUtils.toString(httpResponse.getEntity());

        SeriesList seriesList = xmlMapper.readValue(result, SeriesList.class);

        if (seriesList.getSeries() == null && seriesList.getValue() != null)
        {
            throw new TheTVDBConnectorException(seriesList.getValue());
        }

        return seriesList;
    }
}
