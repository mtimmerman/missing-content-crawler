package com.mtimmerman.security;

import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maarten on 16.01.15.
 */
public class PatchedClientCredentialsTokenEndpointFilter extends ClientCredentialsTokenEndpointFilter {
    private boolean allowOnlyPost = false;

    public PatchedClientCredentialsTokenEndpointFilter() {
        super();
    }

    public PatchedClientCredentialsTokenEndpointFilter(String path) {
        super(path);
        // Set the request matcher to something different
        setRequiresAuthenticationRequestMatcher(new PatchedClientCredentialsRequestMatcher(path));
    }

    protected static class PatchedClientCredentialsRequestMatcher implements RequestMatcher {

        private String path;

        public PatchedClientCredentialsRequestMatcher(String path) {
            this.path = path;

        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String uri = request.getRequestURI();
            int pathParamIndex = uri.indexOf(';');

            if (pathParamIndex > 0) {
                // strip everything after the first semi-colon
                uri = uri.substring(0, pathParamIndex);
            }

            String clientId = request.getParameter("client_id");

            if (clientId == null) {
                // Give basic auth a chance to work instead (it's preferred anyway)
                return false;
            }

            if ("authorization_code".equals(request.getParameter("grant_type"))) {
                // Do not use this filter for authorization code flow, only for password flow.
                return false;
            }

            if ("".equals(request.getContextPath())) {
                return uri.endsWith(path);
            }

            return uri.endsWith(request.getContextPath() + path);
        }

    }
}
