package com.mtimmerman.repositories;

import com.mtimmerman.model.entities.CrawlerInfo;
import com.mtimmerman.model.enums.CrawlerType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by maarten on 29.12.14.
 */
@RepositoryRestResource(exported = false)
public interface CrawlerInfoRepository extends PagingAndSortingRepository<CrawlerInfo, Integer> {
    CrawlerInfo findByCrawlerType(
            @Param("crawlerType") CrawlerType crawlerType
    );
}
