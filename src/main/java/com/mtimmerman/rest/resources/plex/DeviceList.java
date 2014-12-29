package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.net.URI;

/**
 * Created by maarten on 26.12.14.
 */
@JacksonXmlRootElement(localName = "MediaContainer")
public class DeviceList {
    @JacksonXmlProperty(isAttribute = true)
    private String publicAddress;
    @JacksonXmlProperty(localName = "Device")
    private Device[] devices;
    private URI uriUsed;

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public Device[] getDevices() {
        return devices;
    }

    public void setDevices(Device[] devices) {
        this.devices = devices;
    }

    public URI getUriUsed() {
        return uriUsed;
    }

    public void setUriUsed(URI uriUsed) {
        this.uriUsed = uriUsed;
    }
}
