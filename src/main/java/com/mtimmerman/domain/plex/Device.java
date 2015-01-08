package com.mtimmerman.domain.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.Date;

/**
 * Created by maarten on 26.12.14.
 */
public class Device {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String publicAddress;
    @JacksonXmlProperty(isAttribute = true)
    private String product;
    @JacksonXmlProperty(isAttribute = true)
    private String productVersion;
    @JacksonXmlProperty(isAttribute = true)
    private String platform;
    @JacksonXmlProperty(isAttribute = true)
    private String platformVersion;
    @JacksonXmlProperty(isAttribute = true)
    private String device;
    @JacksonXmlProperty(isAttribute = true)
    private String model;
    @JacksonXmlProperty(isAttribute = true)
    private String vendor;
    @JacksonXmlProperty(isAttribute = true)
    private String provides;
    @JacksonXmlProperty(isAttribute = true)
    private String clientIdentifier;
    @JacksonXmlProperty(isAttribute = true)
    private String version;
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private String token;
    @JacksonXmlProperty(isAttribute = true)
    private Date createdAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date lastSeenAt;
    @JacksonXmlProperty(isAttribute = true)
    private String screenResolution;
    @JacksonXmlProperty(isAttribute = true)
    private String screenDensity;

    @JacksonXmlProperty(localName = "Connection")
    private Connection connection;
    @JacksonXmlProperty(localName = "SyncList")
    private SyncList syncList;

    @JacksonXmlText
    private String value;
}
