package com.mtimmerman.rest.resources.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Created by maarten on 29.12.14.
 */
public class AlbumList {
    @JacksonXmlProperty(isAttribute = true)
    private String artist;
    @JacksonXmlProperty(isAttribute = true)
    private Integer page;
    @JacksonXmlProperty(isAttribute = true)
    private Integer perPage;
    @JacksonXmlProperty(isAttribute = true)
    private Integer totalPages;
    @JacksonXmlProperty(isAttribute = true)
    private Integer total;
    @JacksonXmlText
    private String value;

    @JacksonXmlProperty(localName = "album")
    private LastFMAlbum[] lastFMAlbums;

    public LastFMAlbum[] getLastFMAlbums() {
        return lastFMAlbums;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
