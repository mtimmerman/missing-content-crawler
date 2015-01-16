package com.mtimmerman.model.entities.oauth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by maarten on 13.01.15.
 */
@Entity
public class OauthClientToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 256)
    @NotNull()
    private String tokenId;
    @NotNull()
    private byte[] token;
    @Size(max = 256)
    @NotNull()
    private String authenticationId;
    @Size(max = 256)
    @NotNull()
    private String userName;
    @NotNull()
    @Size(max = 256)
    private String clientId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof OauthClientToken) {
            final OauthClientToken other = (OauthClientToken) o;
            return Objects.equals(getTokenId(), other.getTokenId())
                    && Objects.equals(getToken(), other.getToken());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (tokenId != null ? tokenId.hashCode() : 0);
        hash = 89 * hash + Arrays.hashCode(token);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s(tokenId='%s')",
                getClass().getSimpleName(),
                getTokenId());
    }
}

