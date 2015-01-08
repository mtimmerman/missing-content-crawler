package com.mtimmerman.controllers.api;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.repositories.AlbumRepository;
import com.mtimmerman.assemblers.AlbumResourceAssembler;
import com.mtimmerman.resources.AlbumResource;
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
@RequestMapping("/api/albums")
@ExposesResourceFor(Album.class)
public class AlbumController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumResourceAssembler albumResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AlbumResource>> list(
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

        Page<Album> albums =  albumRepository.findAll(
                new PageRequest(
                        page,
                        pageSize
                )
        );
        ArrayList<AlbumResource> albumResources = new ArrayList<>();

        for (Album album : albums){
            AlbumResource albumResource = albumResourceAssembler.toResource(
                    album
            );

            albumResources.add(
                    albumResource
            );
        }
        return new ResponseEntity<List<AlbumResource>>(
                albumResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<AlbumResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        Album album = albumRepository.findOne(
                pk
        );

        AlbumResource albumResource = albumResourceAssembler.toResource(
                album
        );

        return new ResponseEntity<>(
                albumResource,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/not-on-plex", method=RequestMethod.GET)
    public ResponseEntity<List<AlbumResource>> listNotOnPlex(
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
        Page<Album> albums =  albumRepository.findNotOnPlex(
                new PageRequest(
                        page,
                        pageSize
                )
        );

        ArrayList<AlbumResource> albumResources = new ArrayList<>();

        for (Album album : albums){
            AlbumResource albumResource = albumResourceAssembler.toResource(
                    album
            );

            albumResources.add(
                    albumResource
            );
        }
        return new ResponseEntity<List<AlbumResource>>(
                albumResources,
                HttpStatus.OK
        );
    }
}
