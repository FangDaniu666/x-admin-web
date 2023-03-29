package com.daniu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.entity.XUser;
import com.daniu.mapper.XUserMapper;
import com.daniu.service.IXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author FangDaniu
 * @since 2023-03-26
 */
@Service
public class XUserServiceImpl extends ServiceImpl<XUserMapper, XUser> implements IXUserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(XUser user) {
        //根据用户名和密码查询
        LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != user.getUsername(), XUser::getUsername, user.getUsername());
//        wrapper.eq(null != user.getPassword(), XUser::getPassword, user.getPassword());
        XUser loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空则生成token,并将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            String key = "user:" + UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        String json = JSON.toJSONString(redisTemplate.opsForValue().get(token));
        if (json != null) {
            XUser xUser = JSON.parseObject(json, XUser.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", xUser.getUsername());
            data.put("avatar", xUser.getAvatar());
            data.put("avatar", xUser.getAvatar());

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
