package com.mtimmerman.model.entities.oauth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by maarten on 13.01.15.
 */
@Entity
@SequenceGenerator(name = "oauth_approval_approval_id_seq", sequenceName = "oauth_approval_approval_id_seq")
public class OauthApproval implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "oauth_approval_approval_id_seq", strategy = GenerationType.SEQUENCE)
    @NotNull()
    private int approvalId;
    @NotNull()
    private int usrId;
    @NotNull()
    @Size(max = 256)
    private String clientId;
    @Size(max = 256)
    @NotNull()
    private String scope;
    @Size(max = 10)
    @NotNull()
    private String status;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull()
    private Date expiresAt;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @NotNull()
    private Date lastModifiedAt;

    public int getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(int approvalId) {
        this.approvalId = approvalId;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof OauthApproval) {
            final OauthApproval other = (OauthApproval) o;
            return Objects.equals(getUsrId(), other.getUsrId())
                    && Objects.equals(getClientId(), other.getClientId())
                    && Objects.equals(getScope(), other.getScope());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + usrId;
        hash = 31 * hash + (clientId != null ? clientId.hashCode() : 0);
        hash = 31 * hash + (scope != null ? scope.hashCode() : 0);
        return hash;
    }



    @Override
    public String toString() {
        return String.format("%s(usrId='%s', clientId='%s', scope='%s')",
                getClass().getSimpleName(),
                getUsrId(),
                getClientId(),
                getScope());
    }
}
