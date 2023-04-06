package com.daniu.config;

import com.daniu.grab.GetPageUrl;
import com.daniu.utils.PropertiesNiuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Properties;

@Configuration
@EnableScheduling
@Slf4j
@ConditionalOnProperty(prefix = "x-admin-web-app", name = "enabledSchedulingTask", havingValue = "true")
public class SchedulingTaskConfig {
    @Autowired
    private GetPageUrl pageUrl;
    @Autowired
    private Environment env;
    @Autowired
    private Properties properties;
    private int nextPage;
    @Scheduled(initialDelay = 50, fixedDelay = 180000)
    public void myTask() throws Exception {
//        String configProperties = "../resources/config.properties";
//        String configProperties = "./config.properties";
        String configProperties = env.getProperty("x-admin-web-app.configProperties", String.class);
        nextPage = Integer.parseInt(properties.getProperty("nextPage"));
        pageUrl.doGet(nextPage, nextPage, "https://wallhaven.cc/search?purity=100&sorting=views&order=desc&page=");
        nextPage++;
        PropertiesNiuUtils.PropertiesOutputStream(properties, configProperties, "nextPage", String.valueOf(nextPage));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + nextPage);
    }

    //程序关闭后复制config.properties到D:\IntelliJ IDEA\idea-wprkspace\x-admin-web\src\main\resources\config.properties
    /*@PreDestroy
    public void destroy() throws IOException {
        PropertiesNiuUtils.copyProperties(properties, "D:\\IntelliJ IDEA\\idea-wprkspace\\x-admin-web\\src\\main\\resources\\config.properties");
    }*/
}
