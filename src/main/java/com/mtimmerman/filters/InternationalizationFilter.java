package com.mtimmerman.filters;

import com.mtimmerman.model.entities.oauth.OauthClientDetails;
import com.mtimmerman.repositories.oauth.OauthClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by maarten on 13.01.15.
 */
public class InternationalizationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(InternationalizationFilter.class);
    private String localeParam="lang";
    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    private LocaleResolver localeResolver;

    public void setLocaleParam(String localeParam) {
        this.localeParam = localeParam;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
            throws ServletException, IOException {
        List<String> merchantClients = new ArrayList<String>();

        merchantClients.add("citylife-merchant-web-app-preprod");
        merchantClients.add("citylife-merchant-web-app");
        merchantClients.add("citylife-merchant-web-app-dev");
        merchantClients.add("pro-website");

        SavedRequest savedRequest =
                new HttpSessionRequestCache().getRequest(request, response);

        if (savedRequest != null) {
            if (savedRequest.getParameterMap().containsKey("client_id") &&
                    savedRequest.getParameterMap().get("client_id").length > 0) {

                HttpSession httpSession = request.getSession();
                String clientId = savedRequest.getParameterMap().get("client_id")[0];

                Boolean isMerchant = merchantClients.contains(clientId);

                OauthClientDetails oauthClientDetails = oauthClientDetailsRepository.findByClientId(clientId);

                String websiteUri = "";

                if (oauthClientDetails != null && oauthClientDetails.getWebsiteUri() != null) {
                    websiteUri = oauthClientDetails.getWebsiteUri();
                }

                httpSession.setAttribute("isMerchant", isMerchant);
                httpSession.setAttribute("goBackUri", websiteUri);
            }

        }

        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }
        else{
            final String newLocale = request.getParameter(localeParam);
            if (newLocale != null) {
                final Locale locale = StringUtils.parseLocaleString(newLocale.toLowerCase());
                LocaleContextHolder.setLocale(locale);
                LocaleEditor localeEditor = new LocaleEditor();
                localeEditor.setAsText(newLocale);
                localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
                log.debug("change locale to " +
                        locale.getLanguage() + "_" + locale.getCountry() +
                        " at Thread" + Thread.currentThread().getId());
            }
            else{
                final Locale locale=localeResolver.resolveLocale(request);
                LocaleContextHolder.setLocale(locale);
                log.debug("restore locale to " +
                        locale.getLanguage() + "_" + locale.getCountry() +
                        " at Thread" + Thread.currentThread().getId());
            }
            try {
                filterChain.doFilter(request, response);
            } finally {
                LocaleContextHolder.resetLocaleContext();
            }
        }
    }

}