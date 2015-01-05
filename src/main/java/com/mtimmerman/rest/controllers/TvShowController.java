package com.mtimmerman.rest.controllers;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.repositories.EpisodeRepository;
import com.mtimmerman.repositories.SeasonRepository;
import com.mtimmerman.repositories.TvShowRepository;
import com.mtimmerman.rest.assemblers.EpisodeResourceAssembler;
import com.mtimmerman.rest.assemblers.SeasonResourceAssembler;
import com.mtimmerman.rest.assemblers.TvShowResourceAssembler;
import com.mtimmerman.rest.resources.EpisodeResource;
import com.mtimmerman.rest.resources.SeasonResource;
import com.mtimmerman.rest.resources.TvShowResource;
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
@RequestMapping("/tvshows")
@ExposesResourceFor(TvShow.class)
public class TvShowController {
    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private TvShowResourceAssembler tvShowResourceAssembler;
    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;
    @Autowired
    private EpisodeResourceAssembler episodeResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TvShowResource>> list(
            @RequestParam(
                    value= "search",
                    required=false,
                    defaultValue = ""
            ) String search,
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
        Page<TvShow> tvShows;
        PageRequest pageRequest = new PageRequest(
                page,
                pageSize
        );
        if (!search.isEmpty()) {
            tvShows = tvShowRepository.findByTheTVDbNameLike(
                    String.format(
                            "%%%s%%",
                            search.toLowerCase()
                    ),
                    pageRequest
            );
        } else {
            tvShows = tvShowRepository.findAll(
                    pageRequest
            );
        }
        ArrayList<TvShowResource> tvShowResources = new ArrayList<>();

        for (TvShow tvShow : tvShows){
            TvShowResource tvShowResource = tvShowResourceAssembler.toResource(
                    tvShow
            );

            tvShowResources.add(
                    tvShowResource
            );
        }
        return new ResponseEntity<List<TvShowResource>>(
                tvShowResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<TvShowResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        TvShow tvShow = tvShowRepository.findOne(
                pk
        );

        TvShowResource tvShowResource = tvShowResourceAssembler.toResource(
                tvShow
        );

        return new ResponseEntity<>(
                tvShowResource,
                HttpStatus.OK
        );
    }

    private List<EpisodeResource> convertToEpisodeResourceList(List<Episode> episodes) {
        List<EpisodeResource> episodeResources = new ArrayList<>();

        for (Episode episode: episodes) {
            episodeResources.add(
                    episodeResourceAssembler.toResource(
                            episode
                    )
            );
        }

        return episodeResources;
    }

    @RequestMapping(value = "/{pk}/seasons", method= RequestMethod.GET)
    public ResponseEntity<List<SeasonResource>> getSeasonsForTvShow(
            @PathVariable("pk") Integer pk
    ) {
        TvShow tvShow = tvShowRepository.findOne(
                pk
        );

        if (tvShow != null) {
            List<SeasonResource> seasonResources = new ArrayList<>();

            List<Season> seasons = seasonRepository.findByTvShowOrderByTheTVDbSeasonNumberAsc(
                    tvShow
            );

            for (Season season: seasons) {
                seasonResources.add(
                        seasonResourceAssembler.toResource(
                                season
                        )
                );
            }

            return new ResponseEntity<>(
                    seasonResources,
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/{pk}/episodes", method= RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> getEpisodesForTvShow(
            @PathVariable("pk") Integer pk
    ) {
        TvShow tvShow = tvShowRepository.findOne(
                pk
        );

        if (tvShow != null) {
            return new ResponseEntity<>(
                    convertToEpisodeResourceList(
                            episodeRepository.findByTvShow(
                                    tvShow
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/{pk}/episodes-not-on-plex", method= RequestMethod.GET)
    public ResponseEntity<List<EpisodeResource>> getEpisodesForTvShowNotOnPlex(
            @PathVariable("pk") Integer pk
    ) {
        TvShow tvShow = tvShowRepository.findOne(
                pk
        );

        if (tvShow != null) {
            return new ResponseEntity<>(
                    convertToEpisodeResourceList(
                            episodeRepository.findByTvShowNotOnPlex(
                                    tvShow
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }
}
