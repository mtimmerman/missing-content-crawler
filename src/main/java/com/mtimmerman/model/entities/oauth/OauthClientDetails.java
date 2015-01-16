package com.mtimmerman.model.entities.oauth;

import com.mtimmerman.security.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by maarten on 13.01.15.
 */
@Entity
public class OauthClientDetails implements ClientDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 256)
    @NotNull()
    private String clientId;
    @Size(max = 256)
    private String resourceIds;
    @Size(max = 256)
    private String clientSecret;
    @Size(max = 256)
    @NotNull()
    private String scope;
    @Size(max = 256)
    @NotNull()
    private String authorizedGrantTypes;
    @Size(max = 256)
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectURI;
    @Size(max = 256)
    @NotNull()
    private String authorities;
    @NotNull()
    private int accessTokenValidity;
    @NotNull()
    private int refreshTokenValidity;
    @Size(max = 4096)
    private String additionalInformation;
    @Size(max = 256)
    @Column(name = "autoapprove")
    private String autoApprove;
    @Size(max = 256)
    private String websiteUri;

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> result = new HashSet<String>();
        if (resourceIds != null) {
            result.addAll(Arrays.asList(resourceIds.split(",")));
        }
        return result;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public Set<String> getScope() {
        Set<String> result = new HashSet<String>();
        if (scope != null) {
            result.addAll(Arrays.asList(scope.split(",")));
        }
        return result;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> result = new HashSet<String>();
        if (authorizedGrantTypes != null) {
            result.addAll(Arrays.asList(authorizedGrantTypes.split(",")));
        }
        return result;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectURI() {
        return webServerRedirectURI;
    }

    public void setWebServerRedirectURI(String webServerRedirectURI) {
        this.webServerRedirectURI = webServerRedirectURI;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        if (authorities != null) {
            for (String authority : authorities.split(",")) {
                result.add(new Authority(authority));
            }
        }
        return result;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public int getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public int getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(int refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (additionalInformation != null) {
            String[] pairs = additionalInformation.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                result.put(keyValue[0], keyValue[1]);
            }
        }

        return result;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (o instanceof OauthClientDetails) {
            final OauthClientDetails other = (OauthClientDetails) o;
            return Objects.equals(getClientId(), other.getClientId())
                    && Objects.equals(getClientSecret(), other.getClientSecret());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (clientId != null ? clientId.hashCode() : 0);
        hash = 29 * hash + (clientSecret != null ? clientSecret.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s(clientId='%s')",
                getClass().getSimpleName(),
                getClientId());
    }

    @Override
    public boolean isSecretRequired() {
        return clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return scope != null;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> result = new HashSet<String>();
        if (webServerRedirectURI != null) {
            result.addAll(Arrays.asList(webServerRedirectURI.split(",")));
        }
        return result;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValidity;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }
}
