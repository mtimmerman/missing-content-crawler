package com.mtimmerman.security;

import com.mtimmerman.model.entities.oauth.OauthAccessToken;
import com.mtimmerman.repositories.oauth.OauthAccessTokenRepository;
import com.mtimmerman.utils.EnumErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by maarten on 13.01.15.
 */
@Component
public class NoRedirectLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private OauthAccessTokenRepository oauthAccessTokenRepository;
    @Autowired
    private ConsumerTokenServices tokenServices;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null){
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();

            String clientId = httpServletRequest.getParameter("client-id");

            if (clientId != null && !clientId.isEmpty()) {
                List<OauthAccessToken> oauthAccessTokens = oauthAccessTokenRepository.findByUserNameAndClientId(userDetails.getUsername(), clientId);

                if (oauthAccessTokens != null && oauthAccessTokens.size() > 0) {
                    for (OauthAccessToken oauthAccessToken : oauthAccessTokens) {
                        OAuth2AccessToken oAuth2AccessToken = SerializationUtils.deserialize(oauthAccessToken.getToken());

                        if (!tokenServices.revokeToken(oAuth2AccessToken.getValue())) {
                            throw new ServletException(EnumErrorResponse.OAUTH_TOKEN_REVOKE_FAILED.getMessage());
                        }
                    }
                }
            }

            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }
}
