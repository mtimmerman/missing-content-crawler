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
public class OauthRefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id()
    @Size(max = 256)
    @NotNull()
    private String tokenId;
    @NotNull()
    private byte[] token;
    @NotNull()
    private byte[] authentication;

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

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof OauthRefreshToken) {
            final OauthRefreshToken other = (OauthRefreshToken) o;
            return Objects.equals(getTokenId(), other.getTokenId())
                    && Objects.equals(getToken(), other.getToken());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
