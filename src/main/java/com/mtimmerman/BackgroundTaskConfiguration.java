package com.mtimmerman;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by maarten on 05.01.15.
 */
@Profile("bgtasks")
@EnableScheduling
@Configuration
public class BackgroundTaskConfiguration {
}
