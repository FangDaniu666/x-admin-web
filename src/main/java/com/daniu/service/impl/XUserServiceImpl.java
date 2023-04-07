package com.daniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.entity.XMenu;
import com.daniu.entity.XUser;
import com.daniu.entity.XUserRole;
import com.daniu.mapper.XUserMapper;
import com.daniu.mapper.XUserRoleMapper;
import com.daniu.service.IXMenuService;
import com.daniu.service.IXUserService;
import com.daniu.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    /*@Autowired
    private RedisTemplate redisTemplate;*/
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwt;
    @Autowired
    private XUserRoleMapper userRoleMapper;
    @Autowired
    private IXMenuService menuService;

    @Override
    public Map<String, Object> login(XUser user) {
        /*//根据用户名和密码查询
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
        return null;*/
        //根据用户名和密码查询
        LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != user.getUsername(), XUser::getUsername, user.getUsername());
//        wrapper.eq(null != user.getPassword(), XUser::getPassword, user.getPassword());
        XUser loginUser = this.baseMapper.selectOne(wrapper);
        //结果不为空则生成token,并将用户信息存入redis
        if (loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
//            String key = "user:" + UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            //创建jwt
            String key = jwt.createToken(loginUser, JwtUtils.JWT_Default_Expires);
            //返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        /*String json = JSON.toJSONString(redisTemplate.opsForValue().get(token));
        if (json != null) {
            XUser xUser = JSON.parseObject(json, XUser.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", xUser.getUsername());
            data.put("avatar", xUser.getAvatar());

            return data;
        }
        return null;*/
        XUser user = null;
        try {
            user = jwt.parseToken(token, XUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String json = JSON.toJSONString(redisTemplate.opsForValue().get(token));
        if (user != null) {
//            XUser xUser = JSON.parseObject(json, XUser.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name", user.getUsername());
            data.put("avatar", user.getAvatar());
            //根据userId查询roleList
            List<String> roleList = this.baseMapper.getRoleNameByUserId(user.getId());
            data.put("roleList", roleList);
            //权限列表
            List<XMenu> menuList = menuService.getMenuListByUserId(user.getId());
            data.put("menuList", menuList);
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
//        redisTemplate.delete(token);

    }

    @Override
    @Transactional
    public boolean addUser(XUser user) {
        this.baseMapper.insert(user);
        if (user.getRoleIdList() != null) {
            user.getRoleIdList().forEach(roleId -> {
                userRoleMapper.insert(new XUserRole(null, user.getId(), roleId));
            });
            return true;
        }
        return false;
    }

    @Override
    public XUser getUserById(Integer id) {
        XUser user = this.baseMapper.selectById(id);
        LambdaQueryWrapper<XUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XUserRole::getUserId, id);
        List<XUserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Integer> roleIdList = userRoleList.stream().map(XUserRole::getRoleId).collect(Collectors.toList());
        user.setRoleIdList(roleIdList);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(XUser user) {
        this.baseMapper.updateById(user);
        LambdaQueryWrapper<XUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XUserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        if (user.getRoleIdList() != null) {
            user.getRoleIdList().forEach(roleId -> userRoleMapper.insert(new XUserRole(null, user.getId(), roleId)));
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        this.baseMapper.deleteById(id);
        LambdaQueryWrapper<XUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XUserRole::getUserId, id);
        userRoleMapper.delete(wrapper);
    }
}
