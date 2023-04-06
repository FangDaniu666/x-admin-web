package com.daniu.service;

import com.daniu.entity.XRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
public interface IXRoleService extends IService<XRole> {

    boolean addRole(XRole role);

    XRole getRoleById(Integer id);

    void updateRole(XRole role);
}
