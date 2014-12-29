package com.mtimmerman.rest.resources.lastfm;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 29.12.14.
 */
public class LastFMAlbum {
    @JacksonXmlProperty(isAttribute = true)
    private String rank;

    @JacksonXmlProperty
    private String name;
    @JacksonXmlProperty(localName = "playcount")
    private String playCount;
    @JacksonXmlProperty
    private String mbid;
    @JacksonXmlProperty
    private String url;
    @JacksonXmlProperty
    private AlbumArtist artist;
    @JacksonXmlProperty(localName = "image")
    private Image[] images;

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getPlayCount() {
        return playCount;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public AlbumArtist getArtist() {
        return artist;
    }

    public Image[] getImages() {
        return images;
    }
}
