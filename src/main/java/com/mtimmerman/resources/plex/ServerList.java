package com.mtimmerman.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by maarten on 24.12.14.
 */
public class ServerList extends MediaContainer {
    @JacksonXmlProperty(isAttribute = true)
    private String friendlyName;
    @JacksonXmlProperty(isAttribute = true)
    private String identifier;
    @JacksonXmlProperty(isAttribute = true)
    private String machineIdentifier;

    @JacksonXmlProperty(localName = "Server")
    private Server[] servers;

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMachineIdentifier() {
        return machineIdentifier;
    }

    public void setMachineIdentifier(String machineIdentifier) {
        this.machineIdentifier = machineIdentifier;
    }

    public Server[] getServers() {
        return servers;
    }

    public void setServers(Server[] servers) {
        this.servers = servers;
    }
}
