package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class OnDeckList extends MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private Integer allowSync;
    @JacksonXmlProperty(isAttribute = true)
    private String identifier;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagPrefix;
    @JacksonXmlProperty(isAttribute = true)
    private Integer mediaTagVersion;
    @JacksonXmlProperty(isAttribute = true)
    private Integer mixedParents;

    @JacksonXmlProperty(localName = "Video")
    private Video[] videos;

    public Video[] getVideos() {
        return videos;
    }
}
