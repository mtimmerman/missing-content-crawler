package com.mtimmerman;

import com.mtimmerman.filters.InternationalizationFilter;
import com.mtimmerman.service.KickAssConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by maarten on 24.12.14.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableWebMvc
@EnableEntityLinks
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class Application {
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public KickAssConnector kickAssConnector() {
        KickAssConnector kickAssConnector = new KickAssConnector();

        kickAssConnector.setBaseUrl(
                configurableEnvironment.getRequiredProperty(
                        "kickAss.baseUrl"
                )
        );

        return kickAssConnector;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("nl"));
        return localeResolver;
    }

    @Bean
    public InternationalizationFilter internationalizationFilter() {
        InternationalizationFilter internationalizationFilter = new InternationalizationFilter();
        internationalizationFilter.setLocaleParam("lang");
        return internationalizationFilter;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(
                25
        );

        return threadPoolTaskScheduler;
    }

    @Bean(destroyMethod = "shutdown")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(
                5
        );

        threadPoolTaskExecutor.setMaxPoolSize(
                10
        );

        threadPoolTaskExecutor.setQueueCapacity(
                25
        );

        return threadPoolTaskExecutor;
    }
}
