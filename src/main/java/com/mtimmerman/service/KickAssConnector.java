package com.mtimmerman.service;

import com.mtimmerman.model.entities.Torrent;
import com.mtimmerman.repositories.TorrentRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by maarten on 31.12.14.
 */
public class KickAssConnector extends AbstractConnector {
    private String baseUrl;

    @Autowired
    private TorrentRepository torrentRepository;

    private static final Logger log = LoggerFactory.getLogger(
            KickAssConnector.class
    );

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

    public void runCompleteUpdate(String filename) throws IOException {
        if (filename != null) {
            processCSV(
                    null,
                    filename
            );
        } else {
            String uri = String.format(
                    "%s/dailydump.txt.gz",
                    baseUrl
            );

            HttpGet httpGet = createGET(uri);

            HttpResponse httpResponse = handleResponse(httpGet);

            processCSV(
                    httpResponse,
                    null
            );
        }
    }

    public void runHourlyUpdate(String filename) throws IOException {
        if (filename != null)
        {
            processCSV(
                    null,
                    filename
            );
        } else {
            String uri = String.format(
                    "%s/hourlydump.txt.gz",
                    baseUrl
            );

            HttpGet httpGet = createGET(
                    uri
            );

            HttpResponse httpResponse = handleResponse(
                    httpGet
            );

            processCSV(
                    httpResponse,
                    null
            );
        }
    }

    private void processCSV(HttpResponse httpResponse, String filename) throws IOException {
        BufferedReader bufferedReader;

        if (filename != null) {
            bufferedReader = ungzipIt(
                    new FileInputStream(
                            filename
                    )
            );
        } else {
            bufferedReader = ungzipIt(
                    httpResponse.getEntity().getContent()
            );
        }

        if (bufferedReader != null) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    log.info(
                            parts[1]
                    );

                    Torrent torrent = torrentRepository.findByTorrentId(
                            parts[0]
                    );

                    Boolean changed = Boolean.FALSE;

                    if (torrent == null) {
                        torrent = new Torrent();

                        torrent.setTorrentId(
                                parts[0]
                        );

                        changed = Boolean.TRUE;
                    }

                    if (torrent.getName() == null || !torrent.getName().equals(parts[1])) {
                        torrent.setName(
                                parts[1]
                        );

                        changed = Boolean.TRUE;
                    }

                    if (torrent.getType() == null || !torrent.getType().equals(parts[2])) {
                        torrent.setType(
                                parts[2]
                        );

                        changed = Boolean.TRUE;
                    }

                    if (torrent.getUrl() == null || !torrent.getUrl().equals(parts[3])) {
                        torrent.setUrl(
                                parts[3]
                        );

                        changed = Boolean.TRUE;
                    }

                    if (torrent.getTorrentUrl() == null || !torrent.getTorrentUrl().equals(parts[4])) {
                        torrent.setTorrentUrl(
                                parts[4]
                        );

                        changed = Boolean.TRUE;
                    }

                    if (changed) {
                        torrentRepository.save(
                                torrent
                        );
                    }
                } else {
                    log.warn(
                            String.format(
                                    "CSV line had %s parts. Expected 5",
                                    parts.length
                            )
                    );
                }
            }

            bufferedReader.close();
        }
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
