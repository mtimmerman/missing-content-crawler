package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 26.12.14.
 */
public class SyncList {
    @JacksonXmlProperty(isAttribute = true)
    private Integer itemsCompleteCount;
    @JacksonXmlProperty(isAttribute = true)
    private Integer totalSize;
}
