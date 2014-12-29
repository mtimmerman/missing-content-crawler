package com.mtimmerman.rest.resources.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mtimmerman.rest.resources.lastfm.enums.LastFMStatus;

/**
 * Created by maarten on 29.12.14.
 */
@JacksonXmlRootElement(localName = "lfm")
public class LastFMContainer {
    @JacksonXmlProperty(isAttribute = true, localName = "status")
    private LastFMStatus lastFMStatus;

    @JacksonXmlProperty(localName = "topalbums")
    private AlbumList topAlbums;
    @JacksonXmlProperty
    private Error error;

    public LastFMStatus getLastFMStatus() {
        return lastFMStatus;
    }

    public AlbumList getTopAlbums() {
        return topAlbums;
    }

    public Error getError() {
        return error;
    }
}
