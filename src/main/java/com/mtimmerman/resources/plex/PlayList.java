package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mtimmerman.resources.plex.enums.DirectoryContent;
import com.mtimmerman.resources.plex.enums.ViewGroup;

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

    public Directory[] getDirectories() {
        return directories;
    }
}
