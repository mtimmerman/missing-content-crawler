package com.mtimmerman.domain.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.domain.plex.enums.MediaType;

import java.util.Date;

/**
 * Created by maarten on 24.12.14.
 */
public class Video {
    @JacksonXmlProperty(isAttribute = true)
    private Integer allowSync;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionID;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionUUID;
    @JacksonXmlProperty(isAttribute = true)
    private String ratingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private String parentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String studio;
    @JacksonXmlProperty(isAttribute = true)
    private MediaType type;
    @JacksonXmlProperty(isAttribute = true)
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private String titleSort;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String parentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String contentRating;
    @JacksonXmlProperty(isAttribute = true)
    private String summary;
    @JacksonXmlProperty(isAttribute = true)
    private Integer index;
    @JacksonXmlProperty(isAttribute = true)
    private Integer parentIndex;
    @JacksonXmlProperty(isAttribute = true)
    private Double rating;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewCount;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewOffset;
    @JacksonXmlProperty(isAttribute = true)
    private Date lastViewedAt;
    @JacksonXmlProperty(isAttribute = true)
    private String year;
    @JacksonXmlProperty(isAttribute = true)
    private String tagline;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private String art;
    @JacksonXmlProperty(isAttribute = true)
    private String parentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTheme;
    @JacksonXmlProperty(isAttribute = true)
    private Integer duration;
    @JacksonXmlProperty(isAttribute = true)
    private String originallyAvailableAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date addedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date updatedAt;
    @JacksonXmlProperty(isAttribute = true)
    private String chapterSource;
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private String url;
    @JacksonXmlProperty(isAttribute = true)
    private String sourceTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String originalTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String userRating;

    @JacksonXmlProperty(localName = "Media")
    private Media media;
    @JacksonXmlProperty(localName = "Writer")
    private Tag[] writers;
    @JacksonXmlProperty(localName = "Director")
    private Tag director;
    @JacksonXmlProperty(localName = "Genre")
    private Tag[] genres;
    @JacksonXmlProperty(localName = "Role")
    private Tag[] roles;
    @JacksonXmlProperty(localName = "Country")
    private Tag[] country;
    @JacksonXmlProperty(localName = "Collection")
    private Tag[] collections;

    public String getTitle() {
        return title;
    }

    public Integer getIndex() {
        return index;
    }

    public String getKey() {
        return key;
    }
}
