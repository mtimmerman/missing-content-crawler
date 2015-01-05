package com.mtimmerman.rest.assemblers;

import com.mtimmerman.model.entities.CrawlerInfo;
import com.mtimmerman.rest.controllers.CrawlerInfoController;
import com.mtimmerman.rest.resources.CrawlerInfoResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by maarten on 02.01.15.
 */
@Component
public class CrawlerInfoResourceAssembler extends ResourceAssemblerSupport<CrawlerInfo, CrawlerInfoResource> {
    public CrawlerInfoResourceAssembler() {
        super(
                CrawlerInfoController.class,
                CrawlerInfoResource.class
        );
    }

    @Override
    public CrawlerInfoResource toResource(CrawlerInfo crawlerInfo) {
        CrawlerInfoResource crawlerInfoResource = createResourceWithId(
                crawlerInfo.getId(),
                crawlerInfo
        );

        crawlerInfoResource.setCrawlerType(
                crawlerInfo.getCrawlerType()
        );
        crawlerInfoResource.setLastProcessed(
                crawlerInfo.getLastProcessed()
        );
        crawlerInfoResource.setLatestError(
                crawlerInfo.getLatestError()
        );
        crawlerInfoResource.setLatestErrorOn(
                crawlerInfo.getLatestErrorOn()
        );
        crawlerInfoResource.setLatestStackTrace(
                crawlerInfo.getLatestStackTrace()
        );
        crawlerInfoResource.setPk(
                crawlerInfo.getId()
        );
        crawlerInfoResource.setProcessing(
                crawlerInfo.getProcessing()
        );

        return crawlerInfoResource;
    }
}
