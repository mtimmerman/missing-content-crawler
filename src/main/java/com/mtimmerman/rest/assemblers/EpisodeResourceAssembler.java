package com.mtimmerman.rest.assemblers;

import com.mtimmerman.model.entities.Episode;
import com.mtimmerman.model.entities.Season;
import com.mtimmerman.model.entities.TvShow;
import com.mtimmerman.rest.controllers.EpisodeController;
import com.mtimmerman.rest.resources.EpisodeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by maarten on 02.01.15.
 */
@Component
public class EpisodeResourceAssembler extends ResourceAssemblerSupport<Episode, EpisodeResource> {
    @Autowired
    private EntityLinks entityLinks;

    public EpisodeResourceAssembler() {
        super(
                EpisodeController.class,
                EpisodeResource.class
        );
    }

    @Override
    public EpisodeResource toResource(Episode episode) {
        EpisodeResource episodeResource = createResourceWithId(
                episode.getId(),
                episode
        );

        episodeResource.setPk(
                episode.getId()
        );
        episodeResource.setPlexKey(
                episode.getPlexKey()
        );
        episodeResource.setPlexName(
                episode.getPlexName()
        );
        episodeResource.setSearchName(
                episode.getSearchName()
        );
        episodeResource.setTheTVDbEpisodeName(
                episode.getTheTVDbEpisodeName()
        );
        episodeResource.setTheTVDbEpisodeNumber(
                episode.getTheTVDbEpisodeNumber()
        );

        episodeResource.add(
                entityLinks.linkToSingleResource(
                        Season.class,
                        episode.getSeason().getId()
                ).withRel(
                        "season"
                )
        );

        episodeResource.add(
                entityLinks.linkToSingleResource(
                        TvShow.class,
                        episode.getSeason().getTvShow().getId()
                ).withRel(
                        "tvshow"
                )
        );

        return episodeResource;
    }
}
