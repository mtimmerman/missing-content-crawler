package com.mtimmerman.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by maarten on 13.01.15.
 */
public class CORSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain)
            throws ServletException, IOException {
        String origin = httpServletRequest.getHeader("ORIGIN");

        if (origin != null) {
            httpServletResponse.addHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        } else {
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        }
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpServletResponse.addHeader("Access-Control-Max-Age", "1800");//30 min

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}