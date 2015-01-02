package com.mtimmerman;

import org.lightadmin.api.config.LightAdmin;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by maarten on 02.01.15.
 */
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LightAdmin.configure(
                servletContext
        ).basePackage(
                "com.mtimmerman.administration"
        ).baseUrl(
                "/admin"
        ).security(
                false
        );

        super.onStartup(servletContext);

        servletContext.log(
                String.format(
                        "End init %s",
                        servletContext.getServletContextName()
                )
        );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
        return springApplicationBuilder.sources(
                Application.class
        );
    }
}
