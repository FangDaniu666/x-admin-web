package com.daniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.entity.XRole;
import com.daniu.entity.XRoleMenu;
import com.daniu.mapper.XRoleMapper;
import com.daniu.mapper.XRoleMenuMapper;
import com.daniu.service.IXRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
@Service
public class XRoleServiceImpl extends ServiceImpl<XRoleMapper, XRole> implements IXRoleService {

    @Autowired
    private XRoleMenuMapper roleMenuMapper;

    @Override
    @Transactional
    public boolean addRole(XRole role) {
        //写入角色表
        this.baseMapper.insert(role);
        //写入角色表
        if (role.getMenuIdList() != null) {
            role.getMenuIdList().forEach(menuId -> {
                roleMenuMapper.insert(new XRoleMenu(null, role.getRoleId(), menuId));

            });
            return true;
        }
        return false;
    }

    @Override
    public XRole getRoleById(Integer id) {
        XRole role = this.baseMapper.selectById(id);
        if (role != null) {
            /*QueryWrapper<XRoleMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id", id);
            List<XRoleMenu> list = roleMenuMapper.selectList(wrapper);
            role.setMenuIdList(list.stream().map(XRoleMenu::getMenuId).collect(Collectors.toList()));
            System.out.println(role.getMenuIdList());*/
            List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(role.getRoleId());
            role.setMenuIdList(menuIdList);
        }
        return role;
    }

    @Override
    public void updateRole(XRole role) {
        System.out.println(role.getMenuIdList());
        //修改角色表
        this.baseMapper.updateById(role);
        //删除原有权限
        QueryWrapper<XRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", role.getRoleId());
        roleMenuMapper.delete(wrapper);
        //新增权限
        if (role.getMenuIdList() != null) {
            role.getMenuIdList().forEach(menuId -> {
                roleMenuMapper.insert(new XRoleMenu(null,role.getRoleId(), menuId));
            });
        }
    }
}
