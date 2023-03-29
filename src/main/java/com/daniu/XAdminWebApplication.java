package com.daniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.daniu.mapper")
public class XAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(XAdminWebApplication.class, args);
    }

}
