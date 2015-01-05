package com.mtimmerman.rest.resources.thetvdb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 29.12.14.
 */
public class Series {
    @JacksonXmlProperty(localName = "seriesid")
    private Integer seriesId;
    @JacksonXmlProperty
    private String language;
    @JacksonXmlProperty(localName = "SeriesName")
    private String seriesName;
    @JacksonXmlProperty
    private String banner;
    @JacksonXmlProperty(localName = "Overview")
    private String overview;
    @JacksonXmlProperty(localName = "FirstAired")
    private String firstAired;
    @JacksonXmlProperty(localName = "Network")
    private String network;
    @JacksonXmlProperty(localName = "IMDB_ID")
    private String imdbId;
    @JacksonXmlProperty(localName = "zap2it_id")
    private String zap2itId;
    @JacksonXmlProperty(localName = "id")
    private Integer id;
    @JacksonXmlProperty(localName = "AliasNames")
    private String aliasNames;

    public String getSeriesName() {
        return seriesName;
    }

    public Integer getId() {
        return id;
    }
}
