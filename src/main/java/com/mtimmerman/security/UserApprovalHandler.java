package com.mtimmerman.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by maarten on 13.01.15.
 */
public class UserApprovalHandler extends TokenStoreUserApprovalHandler {
    private Collection<String> autoApproveClients = new HashSet<String>();

    private boolean useTokenServices = true;

    public void setAutoApproveClients(Collection<String> autoApproveClients) {
        this.autoApproveClients = autoApproveClients;
    }

    public void setUseTokenServices(boolean useTokenServices) {
        this.useTokenServices = useTokenServices;
    }

    @Override
    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {

        // If we are allowed to check existing approvals this will short circuit the decision
        if (useTokenServices && super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }

        if (!userAuthentication.isAuthenticated()) {
            return false;
        }

//        String flag = authorizationRequest.getApprovalParameters().get(AuthorizationRequest.USER_OAUTH_APPROVAL);
        String flag = "true";
        boolean approved = flag.toLowerCase().equals("true");

        return approved
                || (authorizationRequest.getResponseTypes().contains("token") && autoApproveClients
                .contains(authorizationRequest.getClientId()));
    }
}