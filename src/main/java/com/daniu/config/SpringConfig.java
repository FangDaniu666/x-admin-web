package com.daniu.config;

import com.daniu.grab.SaveUrl;
import com.daniu.utils.JwtUtils;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("com.daniu.grab")
@ComponentScan("com.daniu.utils")
public class SpringConfig {
    @Autowired
    private Environment env;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        //创建连接池
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);
        connectionManager.setDefaultMaxPerRoute(10);
        return connectionManager;
    }
    @Bean
    public Properties properties() throws IOException {
        Properties properties = new Properties();
        //如果找不到config.properties则创建
        String configProperties = env.getProperty("x-admin-web-app.configProperties", String.class);
        File file = new File(configProperties);
        if (!file.exists()) {
            file.createNewFile();
        }
        InputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        inputStream.close();
        return properties;
    }
}
