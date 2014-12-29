package com.mtimmerman.rest.resources.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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

    @JacksonXmlProperty(localName = "album")
    private Album[] albums;

    public String getArtist() {
        return artist;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotal() {
        return total;
    }

    public Album[] getAlbums() {
        return albums;
    }
}
