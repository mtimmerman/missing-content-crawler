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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.HashSet;
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

    @Configuration
    protected static class StatisticResourceConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            super.addResourceHandlers(registry);
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(springResourceTemplateResolver());
        HashSet<IDialect> dialects = new HashSet<>();
        dialects.add(new SpringSecurityDialect());
        dialects.add(new SpringStandardDialect());
        templateEngine.setAdditionalDialects(dialects);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        viewResolver.setOrder(3); // Resolve last, will try to resolve everything
        viewResolver.setViewNames(new String[]{"*"});
        return viewResolver;
    }
}
