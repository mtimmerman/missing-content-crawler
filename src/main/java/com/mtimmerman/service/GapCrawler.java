package com.mtimmerman.service;

import com.mtimmerman.model.entities.CrawlerInfo;
import com.mtimmerman.model.enums.CrawlerType;
import com.mtimmerman.repositories.CrawlerInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by maarten on 29.12.14.
 */
@Component
public class GapCrawler implements ApplicationContextAware {
    @Autowired
    private CrawlerInfoRepository crawlerInfoRepository;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    private Boolean stopped = Boolean.FALSE;

    private static final Logger log = LoggerFactory.getLogger(
            GapCrawler.class
    );

    private ApplicationContext applicationContext;

    private CrawlerInfo getCrawlerInfo(CrawlerType crawlerType) {
        CrawlerInfo crawlerInfo = crawlerInfoRepository.findByCrawlerType(
                crawlerType
        );

        if (crawlerInfo == null) {
            crawlerInfo = new CrawlerInfo();

            crawlerInfo.setCrawlerType(
                    crawlerType
            );

            crawlerInfo.setProcessing(
                    false
            );

            crawlerInfoRepository.save(
                    crawlerInfo
            );
        }

        return crawlerInfo;
    }

    @Scheduled(fixedDelay = 5000)
    public void crawlOverGaps()
            throws InterruptedException {

        log.info("Started crawling");

        Boolean sleeping = Boolean.FALSE;

        while (!stopped) {
            for (CrawlerType crawlerType : CrawlerType.values()) {
                CrawlerInfo crawlerInfo = getCrawlerInfo(
                        crawlerType
                );

                Date currentDate = new Date();

                Integer gap = 10 * 60 * 1000;

                if (!crawlerInfo.getProcessing()) {
                    if (crawlerInfo.getLastProcessed() == null || (currentDate.getTime() - crawlerInfo.getLastProcessed().getTime()) > gap) {
                        sleeping = Boolean.FALSE;

                        crawlerInfo.setProcessing(
                                true
                        );

                        crawlerInfoRepository.save(
                                crawlerInfo
                        );

                        if (crawlerType == CrawlerType.episodes) {
                            TvEpisodeCrawler tvEpisodeCrawler = applicationContext.getBean(
                                    "tvEpisodeCrawler",
                                    TvEpisodeCrawler.class
                            );

                            tvEpisodeCrawler.setCrawlerType(crawlerType);

                            taskExecutor.execute(tvEpisodeCrawler);
                        }
                        if (crawlerType == CrawlerType.albums) {
                            MusicCrawler musicCrawler = applicationContext.getBean(
                                    "musicCrawler",
                                    MusicCrawler.class
                            );

                            musicCrawler.setCrawlerType(crawlerType);

                            taskExecutor.execute(musicCrawler);
                        }
                    }
                }
            }
            if (!sleeping) {
                sleeping = Boolean.TRUE;
                log.info(
                        "Sleeping..."
                );
            }

            Thread.sleep(
                    10000
            );
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setStopped(Boolean stopped) {
        this.stopped = stopped;
    }
}
