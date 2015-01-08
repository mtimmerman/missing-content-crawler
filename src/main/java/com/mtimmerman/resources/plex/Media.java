package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class Media {
    @JacksonXmlProperty(isAttribute = true)
    private String videoResolution;
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private Integer duration;
    @JacksonXmlProperty(isAttribute = true)
    private Integer bitrate;
    @JacksonXmlProperty(isAttribute = true)
    private Integer width;
    @JacksonXmlProperty(isAttribute = true)
    private Integer height;
    @JacksonXmlProperty(isAttribute = true)
    private Double aspectRatio;
    @JacksonXmlProperty(isAttribute = true)
    private Integer audioChannels;
    @JacksonXmlProperty(isAttribute = true)
    private String audioCodec;
    @JacksonXmlProperty(isAttribute = true)
    private String videoCodec;
    @JacksonXmlProperty(isAttribute = true)
    private String container;
    @JacksonXmlProperty(isAttribute = true)
    private String videoFrameRate;
    @JacksonXmlProperty(isAttribute = true)
    private String optimizedForStreaming;
    @JacksonXmlProperty(isAttribute = true)
    private Integer has64bitOffsets;
    @JacksonXmlProperty(isAttribute = true)
    private Integer indirect;
    @JacksonXmlProperty(isAttribute = true)
    private String displayOffset;

    @JacksonXmlProperty(localName = "Part")
    private Part[] parts;
}
