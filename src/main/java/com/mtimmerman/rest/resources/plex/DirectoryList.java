package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.rest.resources.plex.enums.DirectoryContent;
import com.mtimmerman.rest.resources.plex.enums.ViewGroup;

/**
 * Created by maarten on 24.12.14.
 */
public class DirectoryList extends MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private Integer allowSync;
    @JacksonXmlProperty(isAttribute = true)
    private String identifier;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagPrefix;
    @JacksonXmlProperty(isAttribute = true)
    private String mediaTagVersion;
    @JacksonXmlProperty(isAttribute = true)
    private String title1;
    @JacksonXmlProperty(isAttribute = true)
    private String title2;
    @JacksonXmlProperty(isAttribute = true)
    private String art;
    @JacksonXmlProperty(isAttribute = true, localName = "content")
    private DirectoryContent directoryContent;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private ViewGroup viewGroup;
    @JacksonXmlProperty(isAttribute = true)
    private String viewMode;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionID;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String librarySectionUUID;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sortAsc;
    @JacksonXmlProperty(isAttribute = true)
    private String nocache;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private Integer parentIndex;
    @JacksonXmlProperty(isAttribute = true)
    private String parentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String summary;
    @JacksonXmlProperty(isAttribute = true)
    private Integer mixedParents;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTitle;
    @JacksonXmlProperty(isAttribute = true)
    private String parentYear;

    @JacksonXmlProperty(localName = "Directory")
    private Directory[] directories;
    @JacksonXmlProperty(localName = "Video")
    private Video[] videos;
    @JacksonXmlProperty(localName = "Track")
    private Track[] tracks;
    @JacksonXmlProperty(localName = "Photo")
    private Photo[] photos;

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

    public String getMediaTagVersion() {
        return mediaTagVersion;
    }

    public void setMediaTagVersion(String mediaTagVersion) {
        this.mediaTagVersion = mediaTagVersion;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public Directory[] getDirectories() {
        return directories;
    }

    public void setDirectories(Directory[] directories) {
        this.directories = directories;
    }
}
