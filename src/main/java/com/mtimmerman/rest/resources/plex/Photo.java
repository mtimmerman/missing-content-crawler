package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.plex.enums.MediaType;

import java.util.Date;

/**
 * Created by maarten on 26.12.14.
 */
public class Photo {
    @JacksonXmlProperty(isAttribute = true)
    private String ratingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private String parentRatingKey;
    @JacksonXmlProperty(isAttribute = true, localName = "type")
    private MediaType mediaType;
    @JacksonXmlProperty(isAttribute = true)
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private String parentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String summary;
    @JacksonXmlProperty(isAttribute = true)
    private Integer index;
    @JacksonXmlProperty(isAttribute = true)
    private String year;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private String originallyAvailableAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date addedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date updatedAt;
    @JacksonXmlProperty(isAttribute = true)
    private String parentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String parentIndex;
    @JacksonXmlProperty(isAttribute = true)
    private String art;
    @JacksonXmlProperty(isAttribute = true)
    private String parentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentKey;

    @JacksonXmlProperty(isAttribute = true, localName = "Media")
    private Media[] media;
}
