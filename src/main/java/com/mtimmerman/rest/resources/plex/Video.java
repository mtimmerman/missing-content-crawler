package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.plex.enums.MediaType;

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

    public Integer getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(Integer allowSync) {
        this.allowSync = allowSync;
    }

    public String getLibrarySectionID() {
        return librarySectionID;
    }

    public void setLibrarySectionID(String librarySectionID) {
        this.librarySectionID = librarySectionID;
    }

    public String getLibrarySectionTitle() {
        return librarySectionTitle;
    }

    public void setLibrarySectionTitle(String librarySectionTitle) {
        this.librarySectionTitle = librarySectionTitle;
    }

    public String getLibrarySectionUUID() {
        return librarySectionUUID;
    }

    public void setLibrarySectionUUID(String librarySectionUUID) {
        this.librarySectionUUID = librarySectionUUID;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentRatingKey() {
        return parentRatingKey;
    }

    public void setParentRatingKey(String parentRatingKey) {
        this.parentRatingKey = parentRatingKey;
    }

    public String getGrandparentRatingKey() {
        return grandparentRatingKey;
    }

    public void setGrandparentRatingKey(String grandparentRatingKey) {
        this.grandparentRatingKey = grandparentRatingKey;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleSort() {
        return titleSort;
    }

    public void setTitleSort(String titleSort) {
        this.titleSort = titleSort;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrandparentKey() {
        return grandparentKey;
    }

    public void setGrandparentKey(String grandparentKey) {
        this.grandparentKey = grandparentKey;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getGrandparentTitle() {
        return grandparentTitle;
    }

    public void setGrandparentTitle(String grandparentTitle) {
        this.grandparentTitle = grandparentTitle;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getViewOffset() {
        return viewOffset;
    }

    public void setViewOffset(Integer viewOffset) {
        this.viewOffset = viewOffset;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Date getLastViewedAt() {
        return lastViewedAt;
    }

    public void setLastViewedAt(Date lastViewedAt) {
        this.lastViewedAt = lastViewedAt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getParentThumb() {
        return parentThumb;
    }

    public void setParentThumb(String parentThumb) {
        this.parentThumb = parentThumb;
    }

    public String getGrandparentThumb() {
        return grandparentThumb;
    }

    public void setGrandparentThumb(String grandparentThumb) {
        this.grandparentThumb = grandparentThumb;
    }

    public String getGrandparentTheme() {
        return grandparentTheme;
    }

    public void setGrandparentTheme(String grandparentTheme) {
        this.grandparentTheme = grandparentTheme;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getOriginallyAvailableAt() {
        return originallyAvailableAt;
    }

    public void setOriginallyAvailableAt(String originallyAvailableAt) {
        this.originallyAvailableAt = originallyAvailableAt;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getChapterSource() {
        return chapterSource;
    }

    public void setChapterSource(String chapterSource) {
        this.chapterSource = chapterSource;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Tag[] getWriters() {
        return writers;
    }

    public void setWriters(Tag[] writers) {
        this.writers = writers;
    }

    public Tag getDirector() {
        return director;
    }

    public void setDirector(Tag director) {
        this.director = director;
    }

    public Tag[] getGenres() {
        return genres;
    }

    public void setGenres(Tag[] genres) {
        this.genres = genres;
    }

    public Tag[] getRoles() {
        return roles;
    }

    public void setRoles(Tag[] roles) {
        this.roles = roles;
    }

    public Tag[] getCountry() {
        return country;
    }

    public void setCountry(Tag[] country) {
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tag[] getCollections() {
        return collections;
    }

    public void setCollections(Tag[] collections) {
        this.collections = collections;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
