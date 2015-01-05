package com.mtimmerman.rest.resources.thetvdb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.thetvdb.enums.SeriesStatus;

import java.util.Date;

/**
 * Created by maarten on 30.12.14.
 */
public class BaseSeriesRecord {
    @JacksonXmlProperty
    private Integer id;
    @JacksonXmlProperty(localName = "Actors")
    private String actors;
    @JacksonXmlProperty(localName = "Airs_DayOfWeek")
    private String airsDayOfWeek;
    @JacksonXmlProperty(localName = "Airs_Time")
    private String airsTime;
    @JacksonXmlProperty(localName = "ContentRating")
    private String contentRating;
    @JacksonXmlProperty(localName = "FirstAired")
    private String firstAired;
    @JacksonXmlProperty(localName = "Genre")
    private String genre;
    @JacksonXmlProperty(localName = "IMDB_ID")
    private String imdbId;
    @JacksonXmlProperty(localName = "Language")
    private String language;
    @JacksonXmlProperty(localName = "Network")
    private String network;
    @JacksonXmlProperty(localName = "NetworkID")
    private Integer networkId;
    @JacksonXmlProperty(localName = "Overview")
    private String overview;
    @JacksonXmlProperty(localName = "Rating")
    private Double rating;
    @JacksonXmlProperty(localName = "RatingCount")
    private Integer ratingCount;
    @JacksonXmlProperty(localName = "Runtime")
    private Integer runtime;
    @JacksonXmlProperty(localName = "SeriesID")
    private String seriesId;
    @JacksonXmlProperty(localName = "SeriesName")
    private String seriesName;
    @JacksonXmlProperty(localName = "Status")
    private SeriesStatus seriesStatus;
    @JacksonXmlProperty
    private String added;
    @JacksonXmlProperty
    private Integer addedBy;
    @JacksonXmlProperty
    private String banner;
    @JacksonXmlProperty(localName = "fanart")
    private String fanArt;
    @JacksonXmlProperty(localName = "lastupdated")
    private Date lastUpdated;
    @JacksonXmlProperty
    private String poster;
    @JacksonXmlProperty(localName = "zap2it_id")
    private String zapitId;
    @JacksonXmlProperty(localName = "tms_wanted_old")
    private Integer tmsWantedOld;

    public String getSeriesName() {
        return seriesName;
    }
}
