package com.mtimmerman.service;

import com.mtimmerman.resources.thetvdb.FullSeriesRecord;
import com.mtimmerman.resources.thetvdb.Series;
import com.mtimmerman.resources.thetvdb.SeriesList;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by maarten on 29.12.14.
 */
public class TheTVDBConnector extends AbstractConnector {
    private String apiKey;
    private String baseUrl;

    private static final Logger log = LoggerFactory.getLogger(
            TheTVDBConnector.class
    );

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    HttpGet createGET(String uri) {
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

    public FullSeriesRecord getFullSeriesInfo(Integer seriesId)
            throws IOException {
        String uri = String.format(
                "%s/api/%s/series/%s/all/en.zip",
                baseUrl,
                apiKey,
                seriesId
        );

        HttpGet httpGet = createGET(uri);

        HttpResponse httpResponse = handleResponse(httpGet);

        String result = unzipIt(
                EntityUtils.toByteArray(
                        httpResponse.getEntity()
                )
        );

        try {
            return xmlMapper.readValue(result, FullSeriesRecord.class);
        } catch (Exception e) {
            throw e;
        }
    }

    public FullSeriesRecord getSeries(String seriesName)
            throws IOException, TheTVDBConnectorException {
        String uri = String.format(
                "%s/api/GetSeries.php?seriesname=%s",
                baseUrl,
                urlEncodeUTF8(seriesName)
        );

        HttpGet httpGet = createGET(uri);

        HttpResponse httpResponse = handleResponse(httpGet);

        String result = EntityUtils.toString(httpResponse.getEntity());

        SeriesList seriesList = null;

        try {
            seriesList = xmlMapper.readValue(result, SeriesList.class);
        } catch (IOException ignored) {
            return null;
        }

        if (seriesList.getValue() != null && !seriesList.getValue().isEmpty() && !seriesList.getValue().equals("\n"))
        {
            throw new TheTVDBConnectorException(
                    seriesList.getValue()
            );
        }

        if (seriesList.getSeries() != null && seriesList.getSeries().length > 0) {
            if (seriesList.getSeries().length == 1) {
                return getFullSeriesInfo(
                        seriesList.getSeries()[0].getId()
                );
            } else {
                for (Series series: seriesList.getSeries()) {
                    if (series.getSeriesName().equals(seriesName)) {
                        return getFullSeriesInfo(
                                series.getId()
                        );
                    }
                }

                throw new TheTVDBConnectorException(
                        String.format(
                                "Multiple series with name \"%s\" found on TheTVDb but none were an exact match.",
                                seriesName
                        )
                );
            }
        } else {
            log.warn(String.format(
                            "No series with name \"%s\" found on TheTVDb.",
                            seriesName
                    )
            );
        }

        return null;
    }
}
