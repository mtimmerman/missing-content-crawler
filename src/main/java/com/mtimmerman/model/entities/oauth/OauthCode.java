package com.mtimmerman.model.entities.oauth;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by maarten on 13.01.15.
 */
@Entity
public class OauthCode implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id()
    @Size(max = 256)
    @NotNull()
    private String code;
    @NotNull()
    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        if (o instanceof OauthCode) {
            final OauthCode other = (OauthCode) o;
            return Objects.equals(getCode(), other.getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s(code='%s')",
                getClass().getSimpleName(),
                getCode());
    }
}
