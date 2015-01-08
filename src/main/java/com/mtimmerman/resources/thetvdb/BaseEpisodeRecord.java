package com.mtimmerman.resources.thetvdb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

/**
 * Created by maarten on 30.12.14.
 */
public class BaseEpisodeRecord {
    @JacksonXmlProperty
    private Integer id;
    @JacksonXmlProperty(localName = "Combined_episodenumber")
    private Double combinedEpisodeNumber;
    @JacksonXmlProperty(localName = "Combined_season")
    private Double combinedSeason;
    @JacksonXmlProperty(localName = "DVD_chapter")
    private String dvdChapter;
    @JacksonXmlProperty(localName = "DVD_discid")
    private String dvdDiscId;
    @JacksonXmlProperty(localName = "DVD_episodenumber")
    private Double dvdEpisodeNumber;
    @JacksonXmlProperty(localName = "DVD_season")
    private Integer dvdSeason;
    @JacksonXmlProperty(localName = "Director")
    private String director;
    @JacksonXmlProperty(localName = "EpImgFlag")
    private Integer epImgFlag;
    @JacksonXmlProperty(localName = "EpisodeName")
    private String episodeName;
    @JacksonXmlProperty(localName = "EpisodeNumber")
    private Integer EpisodeNumber;
    @JacksonXmlProperty(localName = "FirstAired")
    private String firstAired;
    @JacksonXmlProperty(localName = "GuestStars")
    private String guestStars;
    @JacksonXmlProperty(localName = "IMDB_ID")
    private String imdbId;
    @JacksonXmlProperty(localName = "Language")
    private String language;
    @JacksonXmlProperty(localName = "Overview")
    private String overview;
    @JacksonXmlProperty(localName = "ProductionCode")
    private String productionCode;
    @JacksonXmlProperty(localName = "Rating")
    private Double rating;
    @JacksonXmlProperty(localName = "RatingCount")
    private Integer ratingCount;
    @JacksonXmlProperty(localName = "SeasonNumber")
    private Integer seasonNumber;
    @JacksonXmlProperty(localName = "Writer")
    private String writer;
    @JacksonXmlProperty(localName = "absolute_number")
    private Integer absoluteNumber;
    @JacksonXmlProperty(localName = "airsafter_season")
    private Integer airsAfterSeason;
    @JacksonXmlProperty(localName = "airsbefore_episode")
    private Integer airsBeforeEpisode;
    @JacksonXmlProperty(localName = "airsbefore_season")
    private Integer airsBeforeSeason;
    @JacksonXmlProperty(localName = "filename")
    private String filename;
    @JacksonXmlProperty(localName = "lastupdated")
    private Date lastUpdated;
    @JacksonXmlProperty(localName = "seasonid")
    private Integer seasonId;
    @JacksonXmlProperty(localName = "seriesid")
    private String seriesId;
    @JacksonXmlProperty(localName = "thumb_added")
    private String thumbAdded;
    @JacksonXmlProperty(localName = "thumb_height")
    private Integer thumbHeight;
    @JacksonXmlProperty(localName = "thumb_width")
    private Integer thumbWidth;
    @JacksonXmlProperty(localName = "tms_export")
    private String tmsExport;

    public String getEpisodeName() {
        return episodeName;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return EpisodeNumber;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public String getFilename() {
        return filename;
    }
}
