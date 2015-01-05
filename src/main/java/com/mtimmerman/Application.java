package com.mtimmerman;

import com.mtimmerman.service.KickAssConnector;
import com.mtimmerman.service.LastFMConnector;
import com.mtimmerman.service.PlexConnector;
import com.mtimmerman.service.TheTVDBConnector;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by maarten on 24.12.14.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableWebMvc
@EnableEntityLinks
@EnableScheduling
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class Application {
    @Autowired
    private ConfigurableEnvironment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BasicDataSource dataSource() throws URISyntaxException{
        String url = System.getenv("DATABASE_URL");

        URI dbUri = new URI(
                url
        );

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = String.format(
                "jdbc:postgresql://%s:%s%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory",
                dbUri.getHost(),
                dbUri.getPort(),
                dbUri.getPath()
        );

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(
                dbUrl
        );
        basicDataSource.setUsername(
                username
        );
        basicDataSource.setPassword(
                password
        );


        return basicDataSource;
    }

    @Bean
    public PlexConnector plexConnector() {
        PlexConnector plexConnector = new PlexConnector();

        String username = env.getRequiredProperty(
                "plex.username"
        );

        String password = env.getRequiredProperty(
                "plex.password"
        );

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(
                        username,
                        password
                )
        );

        plexConnector.setCredentialsProvider(
                credentialsProvider
        );

        return plexConnector;
    }

    @Bean
    public LastFMConnector lastFMConnector() {
        LastFMConnector lastFMConnector = new LastFMConnector();

        String apiKey = env.getRequiredProperty(
                "lastFM.apiKey"
        );

        lastFMConnector.setApiKey(
                apiKey
        );

        lastFMConnector.setBaseUrl(
                env.getRequiredProperty(
                        "lastFM.baseUrl"
                )
        );

        return lastFMConnector;
    }

    @Bean
    public TheTVDBConnector theTVDBConnector() {
        TheTVDBConnector theTVDBConnector = new TheTVDBConnector();

        String apiKey = env.getRequiredProperty(
                "theTVDB.apiKey"
        );

        theTVDBConnector.setApiKey(
                apiKey
        );

        theTVDBConnector.setBaseUrl(
                env.getRequiredProperty(
                        "theTVDB.baseUrl"
                )
        );

        return theTVDBConnector;
    }

    @Bean
    public KickAssConnector kickAssConnector() {
        KickAssConnector kickAssConnector = new KickAssConnector();

        kickAssConnector.setBaseUrl(
                env.getRequiredProperty(
                        "kickAss.baseUrl"
                )
        );

        return kickAssConnector;
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
