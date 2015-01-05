package com.mtimmerman.rest.resources.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 29.12.14.
 */
public class AlbumArtist {
    @JacksonXmlProperty
    private String name;
    @JacksonXmlProperty
    private String mbid;
    @JacksonXmlProperty
    private String url;
}
