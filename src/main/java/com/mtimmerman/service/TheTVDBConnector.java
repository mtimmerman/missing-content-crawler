package com.mtimmerman.service;

import com.mtimmerman.rest.resources.thetvdb.FullSeriesRecord;
import com.mtimmerman.rest.resources.thetvdb.Series;
import com.mtimmerman.rest.resources.thetvdb.SeriesList;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
                )X  ²
        );

        return new HttpGet(uri);
    }

    @Override
    HttpPost createPOST(String uri) {
        return null;
    }

    private String unzipIt(byte[] input)
            throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                input
        );

        ZipInputStream zipInputStream = new ZipInputStream(
                byteArrayInputStream
        );

        byte[] buffer = new byte[1024];

        try {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String filename = zipEntry.getName();

                if (filename.equals("en.xml")) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    Integer length;

                    while ((length = zipInputStream.read(buffer)) > 0) {
                        byteArrayOutputStream.write(
                                buffer,
                                0,
                                length
                        );
                    }

                    return new String(
                            byteArrayOutputStream.toByteArray(),
                            "UTF-8"
                    );
                }

                zipEntry = zipInputStream.getNextEntry();
            }
        } finally {
            zipInputStream.closeEntry();
            zipInputStream.close();
        }

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

        if (seriesList.getValue() != null)
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
            throw new TheTVDBConnectorException(
                    String.format(
                            "No series with name \"%s\" found on TheTVDb.",
                            seriesName
                    )
            );
        }
    }
}
