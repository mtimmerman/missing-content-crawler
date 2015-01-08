package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class Tag {
    @JacksonXmlProperty(isAttribute = true)
    private String tag;
}
