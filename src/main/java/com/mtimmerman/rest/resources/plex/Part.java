package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class Part {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private String key;
    @JacksonXmlProperty(isAttribute = true)
    private Integer duration;
    @JacksonXmlProperty(isAttribute = true)
    private String file;
    @JacksonXmlProperty(isAttribute = true)
    private Long size;
    @JacksonXmlProperty(isAttribute = true)
    private String container;
    @JacksonXmlProperty(isAttribute = true)
    private Integer has64bitOffsets;
    @JacksonXmlProperty(isAttribute = true)
    private Integer optimizedForStreaming;
    @JacksonXmlProperty(isAttribute = true)
    private String postURL;
    @JacksonXmlProperty(isAttribute = true)
    private Integer packetLength;
    @JacksonXmlProperty(isAttribute = true)
    private Integer hasChapterTextStream;
    @JacksonXmlProperty(isAttribute = true)
    private Integer hasChapterVideoStream;
    @JacksonXmlProperty(isAttribute = true)
    private Integer orientation;
}
