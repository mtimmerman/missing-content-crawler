package com.mtimmerman.service;

import com.mtimmerman.model.entities.CrawlerInfo;
import com.mtimmerman.model.enums.CrawlerType;
import com.mtimmerman.repositories.CrawlerInfoRepository;
import com.mtimmerman.service.exceptions.GapCrawlerException;
import com.mtimmerman.service.exceptions.LastFMException;
import com.mtimmerman.service.exceptions.PlexServerNotFoundException;
import com.mtimmerman.service.exceptions.TheTVDBConnectorException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;
import java.util.Date;

/**
 * Created by maarten on 05.01.15.
 */
public abstract class AbstractCrawler implements Runnable {
    protected CrawlerType crawlerType;
    protected CrawlerInfo crawlerInfo;
    private StringBuilder stringBuilder;

    private static final Logger log = LoggerFactory.getLogger(
            GapCrawler.class
    );

    public AbstractCrawler() {
        stringBuilder = new StringBuilder();
    }

    @Autowired
    protected CrawlerInfoRepository crawlerInfoRepository;
    @Autowired
    protected PlexConnector plexConnector;
    @Autowired
    private ConfigurableEnvironment env;

    protected String getServerName() {
        return env.getRequiredProperty(
                "plex.server"
        );
    }

    protected void logInfo(String message)
    {
        log.info(
                message
        );

        stringBuilder.append(
                String.format(
                        "INFO:\t%s\n",
                        message
                )
        );
    }

    protected void logWarn(String message)
    {
        log.warn(
                message
        );

        stringBuilder.append(
                String.format(
                        "WARNING:\t%s\n",
                        message
                )
        );
    }

    public void setCrawlerType(CrawlerType crawlerType) {
        this.crawlerType = crawlerType;
    }

    abstract void process() throws
            PlexServerNotFoundException,
            IOException,
            TheTVDBConnectorException,
            GapCrawlerException,
            LastFMException;

    @Override
    public void run() {
        if (crawlerType != null) {
            crawlerInfo = crawlerInfoRepository.findByCrawlerType(crawlerType);

            logInfo(
                    String.format(
                            "Processing %s",
                            crawlerInfo
                    )
            );

            try {
                process();
            } catch (Exception e) {
                logException(e);
            } finally {
                crawlerInfo.setProcessing(
                        false
                );

                crawlerInfo.setLog(
                        stringBuilder.toString()
                );

                crawlerInfoRepository.save(
                        crawlerInfo
                );
            }
        } else {
            logWarn(
                    "No crawler type has been defined."
            );
        }
    }

    private void logException(Exception e) {
        String stackTrace = ExceptionUtils.getStackTrace(e);

        crawlerInfo.setLatestError(
                e.getMessage()
        );
        crawlerInfo.setLatestErrorOn(
                new Date()
        );
        crawlerInfo.setLatestStackTrace(
                stackTrace
        );
    }
}
