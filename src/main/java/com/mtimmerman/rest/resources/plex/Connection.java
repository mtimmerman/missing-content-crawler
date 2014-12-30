package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 26.12.14.
 */
public class Connection {
    @JacksonXmlProperty(isAttribute = true)
    private String uri;
}
