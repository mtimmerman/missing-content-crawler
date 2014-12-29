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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public Integer getHas64bitOffsets() {
        return has64bitOffsets;
    }

    public void setHas64bitOffsets(Integer has64bitOffsets) {
        this.has64bitOffsets = has64bitOffsets;
    }

    public Integer getOptimizedForStreaming() {
        return optimizedForStreaming;
    }

    public void setOptimizedForStreaming(Integer optimizedForStreaming) {
        this.optimizedForStreaming = optimizedForStreaming;
    }
}
