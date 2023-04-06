package com.daniu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "x-admin-web-app", name = "enabledJwtInterceptor", havingValue = "true")
public class SpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    private JwtIntercepter jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                        "/user/login", "/user/info", "/user/logout", "/function/getQrCode", //"/role/*",
                        //放行swagger
                        "/error", "/swagger-ui/**", "/swagger-resources/**", "/v3/**");
    }
}
