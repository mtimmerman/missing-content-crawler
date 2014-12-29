package com.mtimmerman.rest.resources.plex;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by maarten on 24.12.14.
 */
public class Server {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String address;
    @JacksonXmlProperty(isAttribute = true)
    private Integer port;
    @JacksonXmlProperty(isAttribute = true)
    private String version;
    @JacksonXmlProperty(isAttribute = true)
    private String scheme;
    @JacksonXmlProperty(isAttribute = true)
    private String host;
    @JacksonXmlProperty(isAttribute = true)
    private String localAddresses;
    @JacksonXmlProperty(isAttribute = true)
    private String machineIdentifier;
    @JacksonXmlProperty(isAttribute = true)
    private Date createdAt;
    @JacksonXmlProperty(isAttribute = true)
    private Date updatedAt;
    @JacksonXmlProperty(isAttribute = true)
    private Integer owned;
    @JacksonXmlProperty(isAttribute = true)
    private Integer synced;
    @JacksonXmlProperty(isAttribute = true)
    private String accessToken;

    private String serverEndpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLocalAddresses() {
        return localAddresses;
    }

    public void setLocalAddresses(String localAddresses) {
        this.localAddresses = localAddresses;
    }

    public String getMachineIdentifier() {
        return machineIdentifier;
    }

    public void setMachineIdentifier(String machineIdentifier) {
        this.machineIdentifier = machineIdentifier;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOwned() {
        return owned;
    }

    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    public Integer getSynced() {
        return synced;
    }

    public void setSynced(Integer synced) {
        this.synced = synced;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setServerEndpoint(String serverEndpoint) {
        this.serverEndpoint = serverEndpoint;
    }

    public String getServerEndpoint() {
        if (serverEndpoint == null) {
            serverEndpoint = String.format(
                    "%s://%s:%s",
                    scheme,
                    address,
                    port
            );

            try {
                if (checkIsLocal()) {
                    serverEndpoint = String.format(
                            "%s://%s:32400",
                            scheme,
                            localAddresses
                    );
                }
            } catch (IOException ignored) {
            }
        }

        return serverEndpoint;
    }

    private Boolean checkIsLocal() throws IOException {
        Socket socket = null;
        Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        InetAddress maskInetAddress = InetAddress.getByName("255.255.255.0");

        for (; networkInterfaceEnumeration.hasMoreElements(); ) {
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();

            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();

            for (; inetAddressEnumeration.hasMoreElements(); ) {
                InetAddress inetAddress = inetAddressEnumeration.nextElement();

                if (!(inetAddress instanceof Inet6Address)) {
                    InetAddress localInetAddress = InetAddress.getByName(localAddresses);

                    boolean sameNetwork = true;
                    for (int i = 0; i < inetAddress.getAddress().length; i++) {
                        if ((inetAddress.getAddress()[i] & maskInetAddress.getAddress()[i]) !=
                                (localInetAddress.getAddress()[i] & maskInetAddress.getAddress()[i])) {
                            sameNetwork = false;
                            break;
                        }
                    }
                    if (sameNetwork) {
                        try {
                            socket = new Socket(inetAddress.getHostAddress(), 32400);
                            return Boolean.TRUE;
                        } catch (IOException ignored) {
                        } finally {
                            if (socket != null) {
                                try {
                                    socket.close();
                                } catch (IOException ignored) {
                                }
                            }
                        }
                    }

                }
            }
        }
        return Boolean.FALSE;
    }
}
