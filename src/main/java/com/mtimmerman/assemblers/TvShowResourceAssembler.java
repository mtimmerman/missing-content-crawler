package com.mtimmerman.assemblers;

import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.controllers.api.TvShowController;
import com.mtimmerman.resources.TvShowResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by maarten on 02.01.15.
 */
@Component
public class TvShowResourceAssembler extends ResourceAssemblerSupport<TvShow, TvShowResource> {
    public TvShowResourceAssembler() {
        super(
                TvShowController.class,
                TvShowResource.class
        );
    }

    @Override
    public TvShowResource toResource(TvShow tvShow) {
        TvShowResource tvShowResource = createResourceWithId(
                tvShow.getId(),
                tvShow
        );

        tvShowResource.setEpisodesMissing(
                tvShow.getEpisodesMissing()
        );
        tvShowResource.setPk(
                tvShow.getId()
        );
        tvShowResource.setPlexKey(
                tvShow.getPlexKey()
        );
        tvShowResource.setPlexName(
                tvShow.getPlexName()
        );
        tvShowResource.setTheTVDbName(
                tvShow.getTheTVDbName()
        );

        tvShowResource.add(
                linkTo(
                        methodOn(
                                TvShowController.class
                        ).getSeasonsForTvShow(
                                tvShow.getId()
                        )
                ).withRel(
                        "seasons"
                )
        );

        tvShowResource.add(
                linkTo(
                        methodOn(
                                TvShowController.class
                        ).getEpisodesForTvShow(
                                tvShow.getId()
                        )
                ).withRel(
                        "episodes"
                )
        );

        tvShowResource.add(
                linkTo(
                        methodOn(
                                TvShowController.class
                        ).getEpisodesForTvShowNotOnPlex(
                                tvShow.getId()
                        )
                ).withRel(
                        "episodes-not-on-plex"
                )
        );

        return tvShowResource;
    }
}
