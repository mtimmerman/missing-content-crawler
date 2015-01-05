package com.mtimmerman.rest.assemblers;

import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.rest.controllers.SeasonController;
import com.mtimmerman.rest.resources.EpisodeResource;
import com.mtimmerman.rest.resources.SeasonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by maarten on 02.01.15.
 */
@Component
public class SeasonResourceAssembler extends ResourceAssemblerSupport<Season, SeasonResource> {
    @Autowired
    private EntityLinks entityLinks;

    public SeasonResourceAssembler() {
        super(
                SeasonController.class,
                SeasonResource.class
        );
    }

    @Override
    public SeasonResource toResource(Season season) {
        SeasonResource seasonResource = createResourceWithId(
                season.getId(),
                season
        );

        seasonResource.setPk(
                season.getId()
        );
        seasonResource.setPlexKey(
                season.getPlexKey()
        );
        seasonResource.setPlexName(
                season.getPlexName()
        );
        seasonResource.setTheTVDbSeasonId(
                season.getTheTVDbSeasonId()
        );
        seasonResource.setTheTVDbSeasonNumber(
                season.getTheTVDbSeasonNumber()
        );

        seasonResource.add(
                entityLinks.linkToSingleResource(
                        TvShow.class,
                        season.getTvShow().getId()
                ).withRel(
                        "tvshow"
                )
        );

        seasonResource.add(
                linkTo(
                        methodOn(
                                SeasonController.class
                        ).getEpisodesForSeason(
                                season.getId()
                        )
                ).withRel(
                        "episodes"
                )
        );

        seasonResource.add(
                linkTo(
                        methodOn(
                                SeasonController.class
                        ).getEpisodesForSeasonNotOnPlex(
                                season.getId()
                        )
                ).withRel(
                        "episodes-not-on-plex"
                )
        );

        return seasonResource;
    }
}
