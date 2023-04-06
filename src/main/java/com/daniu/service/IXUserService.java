package com.daniu.service;

import com.daniu.entity.XUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FangDaniu
 * @since 2023-03-26
 */
public interface IXUserService extends IService<XUser> {

    Map<String, Object> login(XUser user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    boolean addUser(XUser user);

    XUser getUserById(Integer id);

    void updateUser(XUser user);

    void deleteUser(Integer id);
}
