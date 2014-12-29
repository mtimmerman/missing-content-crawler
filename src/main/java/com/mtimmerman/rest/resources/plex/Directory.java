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

    @JacksonXmlText
    private String value;

    @JacksonXmlProperty(localName = "Location")
    private Location[] locations;

    @JacksonXmlProperty(localName = "Genre")
    private Tag[] genres;
    @JacksonXmlProperty(localName = "Collection")
    private Tag[] collections;

    public Integer getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(Integer allowSync) {
        this.allowSync = allowSync;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public Integer getFilters() {
        return filters;
    }

    public void setFilters(Integer filters) {
        this.filters = filters;
    }

    public Integer getRefreshing() {
        return refreshing;
    }

    public void setRefreshing(Integer refreshing) {
        this.refreshing = refreshing;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DirectoryType getType() {
        return type;
    }

    public void setType(DirectoryType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposite() {
        return composite;
    }

    public void setComposite(String composite) {
        this.composite = composite;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getScanner() {
        return scanner;
    }

    public void setScanner(String scanner) {
        this.scanner = scanner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }
}
