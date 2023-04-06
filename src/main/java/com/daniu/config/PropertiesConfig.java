package com.daniu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "x-admin-web-app")
public class PropertiesConfig {
    private String configProperties;
    private boolean enabledSchedulingTask;
    private boolean enabledJwtInterceptor;
}
