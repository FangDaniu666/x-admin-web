package com.daniu;

import com.daniu.grab.GetPageUrl;
import com.daniu.utils.PropertiesNiuUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
@MapperScan("com.daniu.mapper")
@EnableConfigurationProperties
@Slf4j
public class XAdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(XAdminWebApplication.class, args);
    }
}
