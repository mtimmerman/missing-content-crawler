package com.mtimmerman.rest.controllers;

import com.mtimmerman.model.entities.Album;
import com.mtimmerman.model.entities.Artist;
import com.mtimmerman.repositories.AlbumRepository;
import com.mtimmerman.repositories.ArtistRepository;
import com.mtimmerman.rest.assemblers.AlbumResourceAssembler;
import com.mtimmerman.rest.assemblers.ArtistResourceAssembler;
import com.mtimmerman.rest.resources.AlbumResource;
import com.mtimmerman.rest.resources.ArtistResource;
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
@RequestMapping("/artists")
@ExposesResourceFor(Artist.class)
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistResourceAssembler artistResourceAssembler;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private AlbumResourceAssembler albumResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ArtistResource>> list(
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

        Page<Artist> artists =  artistRepository.findAll(
                new PageRequest(
                        page,
                        pageSize
                )
        );
        ArrayList<ArtistResource> artistResources = new ArrayList<>();

        for (Artist artist : artists){
            ArtistResource artistResource = artistResourceAssembler.toResource(
                    artist
            );

            artistResources.add(
                    artistResource
            );
        }
        return new ResponseEntity<List<ArtistResource>>(
                artistResources,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{pk}", method = RequestMethod.GET)
    public ResponseEntity<ArtistResource> detail(
            @PathVariable("pk") Integer pk
    ) {
        Artist artist = artistRepository.findOne(
                pk
        );

        ArtistResource artistResource = artistResourceAssembler.toResource(
                artist
        );

        return new ResponseEntity<>(
                artistResource,
                HttpStatus.OK
        );
    }

    private List<AlbumResource> convertToAlbumResourceList(List<Album> albums) {
        List<AlbumResource> albumResources = new ArrayList<>();

        for (Album album : albums) {
            albumResources.add(
                    albumResourceAssembler.toResource(
                            album
                    )
            );
        }

        return albumResources;
    }

    @RequestMapping(value = "/{pk}/albums", method= RequestMethod.GET)
    public ResponseEntity<List<AlbumResource>> getAlbumsForArtist(
            @PathVariable("pk") Integer pk
    ) throws ResourceNotFoundException {
        Artist artist = artistRepository.findOne(
                pk
        );

        if (artist != null) {
            return new ResponseEntity<>(
                    convertToAlbumResourceList(
                            albumRepository.findByArtistOrderByLastFMNameAsc(
                                    artist
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }

    @RequestMapping(value = "/{pk}/albums-not-on-plex", method= RequestMethod.GET)
    public ResponseEntity<List<AlbumResource>> getAlbumsForArtistNotOnPlex(
            @PathVariable("pk") Integer pk
    ) throws ResourceNotFoundException {
        Artist artist = artistRepository.findOne(
                pk
        );

        if (artist != null) {
            return new ResponseEntity<>(
                    convertToAlbumResourceList(
                            albumRepository.findByArtistNotOnPlex(
                                    artist
                            )
                    ),
                    HttpStatus.OK
            );
        }

        throw new ResourceNotFoundException();
    }
}
