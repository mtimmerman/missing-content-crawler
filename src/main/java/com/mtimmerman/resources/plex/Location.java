package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class Location {
    @JacksonXmlProperty(isAttribute = true)
    private Integer id;
    @JacksonXmlProperty(isAttribute = true)
    private String path;
}
