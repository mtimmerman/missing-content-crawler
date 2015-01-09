package com.mtimmerman.controllers.api;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.repositories.SeasonRepository;
import com.mtimmerman.assemblers.EpisodeResourceAssembler;
import com.mtimmerman.assemblers.SeasonResourceAssembler;
import com.mtimmerman.resources.EpisodeResource;
import com.mtimmerman.resources.SeasonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
@RequestMapping("/api/seasons")
@ExposesResourceFor(Season.class)
public class SeasonController {
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;
    @Autowired
    private EpisodeResourceAssembler episodeResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SeasonResource>> list(
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

        Page<Season> seasons =  seasonRepository.findAll(
                new PageRequest(
                        page,
                        pageSize
                )
        );
        ArrayList<SeasonResource> seasonResources = new ArrayList<>();

        for (Season season : seasons){
            SeasonResource seasonResource = seasonResourceAssembler.toResource(
                    season
            );

            seasonResources.add(
                    seasonResource
            );
        }
        return new ResponseEntity<List<SeasonResource>>(
                seasonResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<SeasonResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        Season season = seasonRepository.findOne(
                pk
        );

        SeasonResource seasonResource = seasonResourceAssembler.toResource(season);

        return new ResponseEntity<>(
                seasonResource,
                HttpStatus.OK
        );
    }

    private List<EpisodeResource> convertToEpisodeResourceList(List<Episode> episodes) {
        List<EpisodeResource> episodeResources = new ArrayList<>();

        for (Episode episode : episodes) {
            episodeResources.add(
                    episodeResourceAssembler.toResource(
                            episode
                    )
            );
        }

        return episodeResources;
    }

    @RequestMapping(value = "/{pk}/episodes", method= RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> getEpisodesForSeason(
            @PathVariable("pk") Integer pk
    ) throws ResourceNotFoundException {
        Season season = seasonRepository.findOne(
                pk
        );

        if (season != null) {
            return new ResponseEntity<>(
                    convertToEpisodeResourceList(
                            episodeRepository.findBySeasonOrderByTheTVDbEpisodeNumberAsc(
                                    season
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/{pk}/episodes-not-on-plex", method= RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> getEpisodesForSeasonNotOnPlex(
            @PathVariable("pk") Integer pk
    ) throws ResourceNotFoundException {
        Season season = seasonRepository.findOne(
                pk
        );

        if (season != null) {
            return new ResponseEntity<>(
                    convertToEpisodeResourceList(
                            episodeRepository.findBySeasonNotOnPlex(
                                    season
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }
}
