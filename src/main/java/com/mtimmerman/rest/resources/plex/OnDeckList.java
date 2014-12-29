package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class OnDeckList extends MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private Integer allowSync;
    @JacksonXmlProperty(isAttribute = true)
    private String identifier;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagPrefix;
    @JacksonXmlProperty(isAttribute = true)
    private Integer mediaTagVersion;
    @JacksonXmlProperty(isAttribute = true)
    private Integer mixedParents;

    @JacksonXmlProperty(localName = "Video")
    private Video[] videos;

    public Integer getAllowSync() {
        return allowSync;
    }

    public void setAllowSync(Integer allowSync) {
        this.allowSync = allowSync;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMediaTagPrefix() {
        return mediaTagPrefix;
    }

    public void setMediaTagPrefix(String mediaTagPrefix) {
        this.mediaTagPrefix = mediaTagPrefix;
    }

    public Integer getMediaTagVersion() {
        return mediaTagVersion;
    }

    public void setMediaTagVersion(Integer mediaTagVersion) {
        this.mediaTagVersion = mediaTagVersion;
    }

    public Integer getMixedParents() {
        return mixedParents;
    }

    public void setMixedParents(Integer mixedParents) {
        this.mixedParents = mixedParents;
    }

    public Video[] getVideos() {
        return videos;
    }

    public void setVideos(Video[] videos) {
        this.videos = videos;
    }
}
