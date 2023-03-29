package com.daniu;

import com.daniu.entity.XUser;
import com.daniu.mapper.XUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MD5Hex {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void md5Hex(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
