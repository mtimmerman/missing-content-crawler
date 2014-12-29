package com.mtimmerman;

import com.mtimmerman.service.LastFMConnector;
import com.mtimmerman.service.PlexConnector;
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

/**
 * Created by maarten on 24.12.14.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    @Autowired
    private ConfigurableEnvironment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PlexConnector plexConnector() {
        PlexConnector plexConnector = new PlexConnector();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(
                        env.getRequiredProperty("plex.username"),
                        env.getRequiredProperty("plex.password")
                )
        );

        plexConnector.setCredentialsProvider(credentialsProvider);

        return plexConnector;
    }

    @Bean
    public LastFMConnector lastFMConnector() {
        LastFMConnector lastFMConnector = new LastFMConnector();

        lastFMConnector.setApiKey(
                env.getRequiredProperty("lastFM.apiKey")
        );

        lastFMConnector.setApiSecret(
                env.getRequiredProperty("lastFM.apiSecret")
        );

        lastFMConnector.setBaseUrl(
                env.getRequiredProperty("lastFM.baseUrl")
        );

        return lastFMConnector;
    }
}
