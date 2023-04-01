package com.daniu;

import com.daniu.entity.XUser;
import com.daniu.mapper.XUserMapper;
import com.daniu.service.impl.ImgsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XAdminWebApplicationTests {

    @Autowired
    private XUserMapper userMapper;
    @Autowired
    private ImgsServiceImpl imgsService;
    @Test
    void contextLoads() {
        List<XUser> xUsers = userMapper.selectList(null);
        xUsers.forEach(System.out::println);
        System.out.println(imgsService.toString());
    }

}
