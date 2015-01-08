package com.mtimmerman.assemblers;

import com.mtimmerman.model.entities.Artist;
import com.mtimmerman.controllers.api.ArtistController;
import com.mtimmerman.resources.ArtistResource;
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
public class ArtistResourceAssembler extends ResourceAssemblerSupport<Artist, ArtistResource> {
    @Autowired
    private EntityLinks entityLinks;

    public ArtistResourceAssembler() {
        super(
                ArtistController.class,
                ArtistResource.class
        );
    }

    @Override
    public ArtistResource toResource(Artist artist) {
        ArtistResource artistResource = createResourceWithId(
                artist.getId(),
                artist
        );

        artistResource.setLastFMName(artist.getLastFMName());
        artistResource.setPk(artist.getId());
        artistResource.setPlexKey(artist.getPlexKey());
        artistResource.setPlexName(artist.getPlexName());

        artistResource.add(
                linkTo(
                        methodOn(
                                ArtistController.class
                        ).getAlbumsForArtist(
                                artist.getId()
                        )
                ).withRel(
                        "albums"
                )
        );

        artistResource.add(
                linkTo(
                        methodOn(
                                ArtistController.class
                        ).getAlbumsForArtistNotOnPlex(
                                artist.getId()
                        )
                ).withRel(
                        "albums-not-on-plex"
                )
        );

        return artistResource;
    }
}
