package com.mtimmerman.controllers.api;

import com.mtimmerman.model.entities.CrawlerInfo;
import com.mtimmerman.repositories.CrawlerInfoRepository;
import com.mtimmerman.assemblers.CrawlerInfoResourceAssembler;
import com.mtimmerman.resources.CrawlerInfoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maarten on 05.01.15.
 */
@RestController
@RequestMapping("/api/crawlerinfos")
@ExposesResourceFor(CrawlerInfo.class)
public class CrawlerInfoController {
    @Autowired
    private CrawlerInfoRepository crawlerInfoRepository;
    @Autowired
    private CrawlerInfoResourceAssembler crawlerInfoResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CrawlerInfoResource>> list(
            @RequestParam(
                    value = "page",
                    required=false,
                    defaultValue = "0"
            ) Integer page,
            @RequestParam(
                    value="page_size",
                    required=false,
                    defaultValue = "20"
            ) Integer pageSize
    ) {

        Page<CrawlerInfo> crawlerInfos =  crawlerInfoRepository.findAll(
                new PageRequest(
                        page,
                        pageSize
                )
        );
        ArrayList<CrawlerInfoResource> crawlerInfoResources = new ArrayList<>();

        for (CrawlerInfo crawlerInfo : crawlerInfos){
            CrawlerInfoResource crawlerInfoResource = crawlerInfoResourceAssembler.toResource(
                    crawlerInfo
            );

            crawlerInfoResources.add(
                    crawlerInfoResource
            );
        }
        return new ResponseEntity<List<CrawlerInfoResource>>(
                crawlerInfoResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<CrawlerInfoResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        CrawlerInfo crawlerInfo = crawlerInfoRepository.findOne(
                pk
        );

        CrawlerInfoResource crawlerInfoResource = crawlerInfoResourceAssembler.toResource(
                crawlerInfo
        );

        return new ResponseEntity<>(
                crawlerInfoResource,
                HttpStatus.OK
        );
    }
}
