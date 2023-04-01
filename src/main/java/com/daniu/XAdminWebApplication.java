package com.daniu;

import com.daniu.grab.GetPageUrl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@MapperScan("com.daniu.mapper")
@EnableScheduling
public class XAdminWebApplication {
    @Autowired
    private GetPageUrl pageUrl;
    private int nextPage = 11;
    public static void main(String[] args) {
        SpringApplication.run(XAdminWebApplication.class, args);
    }

    @Scheduled(cron = "0 0/3 * * * ?")
    public void myTask() throws Exception {
        pageUrl.doGet(nextPage,nextPage,"https://wallhaven.cc/search?purity=100&sorting=views&order=desc&page=");
        nextPage++;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+nextPage);
    }

}
