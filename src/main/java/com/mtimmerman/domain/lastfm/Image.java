package com.mtimmerman.domain.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.mtimmerman.domain.lastfm.enums.ImageSize;

/**
 * Created by maarten on 29.12.14.
 */
public class Image {
    @JacksonXmlProperty(localName = "size", isAttribute = true)
    private ImageSize imageSize;
    @JacksonXmlText
    private String url;
}
