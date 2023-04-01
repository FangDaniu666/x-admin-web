package com.daniu.config;

import com.daniu.grab.SaveUrl;
import com.daniu.utils.JwtUtils;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.daniu.grab")
@ComponentScan("com.daniu.utils")
public class SpringConfig {
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
}
