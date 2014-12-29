package com.mtimmerman.rest.resources.plex;

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

    @JacksonXmlProperty(localName = "Part")
    private Part[] parts;

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Integer getAudioChannels() {
        return audioChannels;
    }

    public void setAudioChannels(Integer audioChannels) {
        this.audioChannels = audioChannels;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public void setAudioCodec(String audioCodec) {
        this.audioCodec = audioCodec;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(String videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public String getOptimizedForStreaming() {
        return optimizedForStreaming;
    }

    public void setOptimizedForStreaming(String optimizedForStreaming) {
        this.optimizedForStreaming = optimizedForStreaming;
    }

    public Integer getHas64bitOffsets() {
        return has64bitOffsets;
    }

    public void setHas64bitOffsets(Integer has64bitOffsets) {
        this.has64bitOffsets = has64bitOffsets;
    }

    public Part[] getParts() {
        return parts;
    }

    public void setParts(Part[] parts) {
        this.parts = parts;
    }
}
