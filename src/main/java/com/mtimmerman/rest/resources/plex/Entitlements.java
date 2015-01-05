package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Created by maarten on 26.12.14.
 */
public class Entitlements {
    @JacksonXmlProperty(isAttribute = true)
    private Integer all;

    @JacksonXmlText
    private String value;
}
