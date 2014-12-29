package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.net.URI;

/**
 * Created by maarten on 24.12.14.
 */
@JacksonXmlRootElement(localName = "MediaContainer")
public abstract class MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private Integer size;
    @JacksonXmlText
    private String value;

    private URI uriUsed;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public URI getUriUsed() {
        return uriUsed;
    }

    public void setUriUsed(URI uriUsed) {
        this.uriUsed = uriUsed;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
