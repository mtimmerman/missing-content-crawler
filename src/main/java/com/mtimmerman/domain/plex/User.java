package com.mtimmerman.domain.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.net.URI;

/**
 * Created by maarten on 26.12.14.
 */
public class User {
    @JacksonXmlProperty(isAttribute = true)
    private String email;
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private String thumb;
    @JacksonXmlProperty(isAttribute = true)
    private String username;
    @JacksonXmlProperty(isAttribute = true)
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private String cloudSyncDevice;
    @JacksonXmlProperty(isAttribute = true)
    private String locale;
    @JacksonXmlProperty(isAttribute = true)
    private String authenticationToken;
    @JacksonXmlProperty(isAttribute = true)
    private String restricted;
    @JacksonXmlProperty(isAttribute = true)
    private String home;
    @JacksonXmlProperty(isAttribute = true)
    private String queueEmail;
    @JacksonXmlProperty(isAttribute = true)
    private String queueUid;

    private URI uriUsed;

    @JacksonXmlProperty(localName = "joined-at")
    private String joinedAt;
    @JacksonXmlProperty(localName = "entitlements")
    private Entitlements entitlements;
    @JacksonXmlProperty(localName = "authentication-token")
    private String authenticationToken2;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setUriUsed(URI uriUsed) {
        this.uriUsed = uriUsed;
    }

    public String getAuthenticationToken2() {
        return authenticationToken2;
    }
}
