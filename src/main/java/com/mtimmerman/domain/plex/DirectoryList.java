package com.mtimmerman.domain.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.domain.plex.enums.DirectoryContent;
import com.mtimmerman.domain.plex.enums.ViewGroup;

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
    @JacksonXmlProperty(isAttribute = true)
    private String banner;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentContentRating;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentStudio;
    @JacksonXmlProperty(isAttribute = true)
    private String theme;
    @JacksonXmlProperty(isAttribute = true)
    private String grandparentTheme;


    @JacksonXmlProperty(localName = "Directory")
    private Directory[] directories;
    @JacksonXmlProperty(localName = "Video")
    private Video[] videos;
    @JacksonXmlProperty(localName = "Track")
    private Track[] tracks;
    @JacksonXmlProperty(localName = "Photo")
    private Photo[] photos;

    public Directory[] getDirectories() {
        return directories;
    }

    public Video[] getVideos() {
        return videos;
    }
}
