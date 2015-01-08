package com.mtimmerman.assemblers;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.model.entities.Artist;
import com.mtimmerman.controllers.AlbumController;
import com.mtimmerman.resources.AlbumResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Created by maarten on 02.01.15.
 */
@Component
public class AlbumResourceAssembler extends ResourceAssemblerSupport<Album, AlbumResource> {
    @Autowired
    private EntityLinks entityLinks;

    public AlbumResourceAssembler() {
        super(
                AlbumController.class,
                AlbumResource.class
        );
    }

    @Override
    public AlbumResource toResource(Album album) {
        AlbumResource albumResource = createResourceWithId(
                album.getId(),
                album
        );

        albumResource.setLastFMmbId(
                album.getLastFMmbId()
        );
        albumResource.setLastFMName(
                album.getLastFMName()
        );
        albumResource.setPk(
                album.getId()
        );
        albumResource.setPlexKey(
                album.getPlexKey()
        );
        albumResource.setPlexName(
                album.getPlexName()
        );

        albumResource.add(
                entityLinks.linkToSingleResource(
                        Artist.class,
                        album.getArtist().getId()
                ).withRel(
                        "artist"
                )
        );

        return albumResource;
    }
}
