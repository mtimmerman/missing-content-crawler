package com.mtimmerman.rest.resources.plex;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCloudSyncDevice() {
        return cloudSyncDevice;
    }

    public void setCloudSyncDevice(String cloudSyncDevice) {
        this.cloudSyncDevice = cloudSyncDevice;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getRestricted() {
        return restricted;
    }

    public void setRestricted(String restricted) {
        this.restricted = restricted;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getQueueEmail() {
        return queueEmail;
    }

    public void setQueueEmail(String queueEmail) {
        this.queueEmail = queueEmail;
    }

    public String getQueueUid() {
        return queueUid;
    }

    public void setQueueUid(String queueUid) {
        this.queueUid = queueUid;
    }

    public Entitlements getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(Entitlements entitlements) {
        this.entitlements = entitlements;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getAuthenticationToken2() {
        return authenticationToken2;
    }

    public void setAuthenticationToken2(String authenticationToken2) {
        this.authenticationToken2 = authenticationToken2;
    }

    public URI getUriUsed() {
        return uriUsed;
    }

    public void setUriUsed(URI uriUsed) {
        this.uriUsed = uriUsed;
    }
}
