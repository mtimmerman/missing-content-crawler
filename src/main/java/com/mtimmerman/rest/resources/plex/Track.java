package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.plex.enums.MediaType;

import java.util.Date;

/**
 * Created by maarten on 26.12.14.
 */
public class Track {
    @JacksonXmlProperty(isAttribute = true)
    private String ratingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private String parentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private MediaType type;
    @JacksonXmlProperty(isAttribute = true)
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private String parentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String parentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String originalTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String summary;
    @JacksonXmlProperty(isAttribute = true)
    private Integer index;
    @JacksonXmlProperty(isAttribute = true)
    private Integer parentIndex;
    @JacksonXmlProperty(isAttribute = true)
    private String parentYear;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private String parentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private Long duration;
    @JacksonXmlProperty(isAttribute = true)
    private Date addedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date updatedAt;
    @JacksonXmlProperty(isAttribute = true)
    private String titleSort;
    @JacksonXmlProperty(isAttribute = true)
    private Date lastViewedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewCount;
    @JacksonXmlProperty(isAttribute = true)
    private String chapterSource;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewOffset;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private String art;

    @JacksonXmlProperty(localName = "Media")
    private Media[] media;
}
