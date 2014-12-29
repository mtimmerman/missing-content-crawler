package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.plex.enums.DirectoryContent;
import com.mtimmerman.rest.resources.plex.enums.ViewGroup;

/**
 * Created by maarten on 26.12.14.
 */
public class PlayList extends MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private String friendlyName;
    @JacksonXmlProperty(isAttribute = true)
    private String identifier;
    @JacksonXmlProperty(isAttribute = true)
    private String machineIdentifier;
    @JacksonXmlProperty(isAttribute = true, localName = "content")
    private DirectoryContent playListContent;
    @JacksonXmlProperty(isAttribute = true)
    private String title1;
    @JacksonXmlProperty(isAttribute = true)
    private String title2;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagPrefix;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagVersion;
    @JacksonXmlProperty(isAttribute = true)
    private ViewGroup viewGroup;

    @JacksonXmlProperty( localName = "Directory")
    private Directory[] directories;

    @JacksonXmlProperty( localName = "Video")
    private Video[] videos;

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMachineIdentifier() {
        return machineIdentifier;
    }

    public void setMachineIdentifier(String machineIdentifier) {
        this.machineIdentifier = machineIdentifier;
    }

    public Directory[] getDirectories() {
        return directories;
    }

    public void setDirectories(Directory[] directories) {
        this.directories = directories;
    }

    public DirectoryContent getPlayListContent() {
        return playListContent;
    }

    public void setPlayListContent(DirectoryContent playListContent) {
        this.playListContent = playListContent;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getMediaTagPrefix() {
        return mediaTagPrefix;
    }

    public void setMediaTagPrefix(String mediaTagPrefix) {
        this.mediaTagPrefix = mediaTagPrefix;
    }

    public String getMediaTagVersion() {
        return mediaTagVersion;
    }

    public void setMediaTagVersion(String mediaTagVersion) {
        this.mediaTagVersion = mediaTagVersion;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public Video[] getVideos() {
        return videos;
    }

    public void setVideos(Video[] videos) {
        this.videos = videos;
    }
}
