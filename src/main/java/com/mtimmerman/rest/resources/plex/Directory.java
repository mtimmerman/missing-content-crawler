package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.mtimmerman.rest.resources.plex.enums.DirectoryType;

import java.util.Date;


/**
 * Created by maarten on 24.12.14.
 */
public class Directory {
    @JacksonXmlProperty(isAttribute = true)
    private Integer allowSync;
    @JacksonXmlProperty(isAttribute = true)
    private String art;
    @JacksonXmlProperty(isAttribute = true)
    private Integer filters;
    @JacksonXmlProperty(isAttribute = true)
    private Integer refreshing;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private DirectoryType type;
    @JacksonXmlProperty(isAttribute = true)
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private String composite;
    @JacksonXmlProperty(isAttribute = true)
    private String agent;
    @JacksonXmlProperty(isAttribute = true)
    private String scanner;
    @JacksonXmlProperty(isAttribute = true)
    private String language;
    @JacksonXmlProperty(isAttribute = true)
    private String uuid;
    @JacksonXmlProperty(isAttribute = true)
    private Date updatedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date createdAt;
    @JacksonXmlProperty(isAttribute = true)
    private Integer secondary;
    @JacksonXmlProperty(isAttribute = true)
    private String prompt;
    @JacksonXmlProperty(isAttribute = true)
    private Integer search;
    @JacksonXmlProperty(isAttribute = true)
    private Integer size;
    @JacksonXmlProperty(isAttribute = true)
    private String ratingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String parentRatingKey;
    @JacksonXmlProperty(isAttribute = true)
    private String parentKey;
    @JacksonXmlProperty(isAttribute = true)
    private String parentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String summary;
    @JacksonXmlProperty(isAttribute = true)
    private Integer index;
    @JacksonXmlProperty(isAttribute = true)
    private String parentThumb;
    @JacksonXmlProperty(isAttribute = true)
    private Integer leafCount;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewedLeafCount;
    @JacksonXmlProperty(isAttribute = true)
    private Date addedAt;
    @JacksonXmlProperty(isAttribute = true)
    private String year;
    @JacksonXmlProperty(isAttribute = true)
    private String originallyAvailableAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date lastViewedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Integer viewCount;
    @JacksonXmlProperty(isAttribute = true)
    private String titleSort;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionID;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionUUID;
    @JacksonXmlProperty(isAttribute = true)
    private String studio;
    @JacksonXmlProperty(isAttribute = true)
    private String contentRating;
    @JacksonXmlProperty(isAttribute = true)
    private String rating;
    @JacksonXmlProperty(isAttribute = true)
    private String banner;
    @JacksonXmlProperty(isAttribute = true)
    private Long duration;
    @JacksonXmlProperty(isAttribute = true)
    private Integer childCount;
    @JacksonXmlProperty(isAttribute = true)
    private String theme;
    @JacksonXmlProperty(isAttribute = true)
    private String userRating;

    @JacksonXmlText
    private String value;

    @JacksonXmlProperty(localName = "Location")
    private Location[] locations;

    @JacksonXmlProperty(localName = "Genre")
    private Tag[] genres;
    @JacksonXmlProperty(localName = "Collection")
    private Tag[] collections;
    @JacksonXmlProperty(localName = "Role")
    private Tag[] role;

    public String getKey() {
        return key;
    }

    public DirectoryType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIndex() {
        return index;
    }
}
