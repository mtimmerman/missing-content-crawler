package com.mtimmerman.controllers.api;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.assemblers.EpisodeResourceAssembler;
import com.mtimmerman.resources.EpisodeResource;
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
 * Created by maarten on 02.01.15.
 */
@RestController
@RequestMapping("/api/episodes")
@ExposesResourceFor(Episode.class)
public class EpisodeController {
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private EpisodeResourceAssembler episodeResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> list(
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

        Page<Episode> episodes =  episodeRepository.findAll(
                new PageRequest(
                        page,
                        pageSize
                )
        );
        ArrayList<EpisodeResource> episodeResources = new ArrayList<>();

        for (Episode episode : episodes){
            EpisodeResource episodeResource = episodeResourceAssembler.toResource(
                    episode
            );

            episodeResources.add(
                    episodeResource
            );
        }
        return new ResponseEntity<List<EpisodeResource>>(
                episodeResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<EpisodeResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        Episode episode = episodeRepository.findOne(
                pk
        );

        EpisodeResource episodeResource = episodeResourceAssembler.toResource(
                episode
        );

        return new ResponseEntity<>(
                episodeResource,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/not-on-plex", method=RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> listNotOnPlex(
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
        Page<Episode> episodes =  episodeRepository.findNotOnPlex(
                new PageRequest(
                        page,
                        pageSize
                )
        );

        ArrayList<EpisodeResource> episodeResources = new ArrayList<>();

        for (Episode episode : episodes){
            EpisodeResource episodeResource = episodeResourceAssembler.toResource(
                    episode
            );

            episodeResources.add(
                    episodeResource
            );
        }
        return new ResponseEntity<List<EpisodeResource>>(
                episodeResources,
                HttpStatus.OK
        );
    }
}
