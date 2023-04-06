package com.daniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daniu.entity.XMenu;
import com.daniu.mapper.XMenuMapper;
import com.daniu.service.IXMenuService;
import org.springframework.stereotype.Service;

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
public class XMenuServiceImpl extends ServiceImpl<XMenuMapper, XMenu> implements IXMenuService {

    @Override
    public List<XMenu> getAllMenu() {
        //一级菜单
        LambdaQueryWrapper<XMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XMenu::getParentId, 0);
        List<XMenu> menuList = this.list(wrapper);
        //填充子菜单
        if (menuList != null) {
            menuList.forEach(menu -> {
                LambdaQueryWrapper<XMenu> subWrapper = new LambdaQueryWrapper<>();
                subWrapper.eq(XMenu::getParentId, menu.getMenuId());
                List<XMenu> subMenuList = this.list(subWrapper);
                menu.setChildren(subMenuList);
            });
        }
        return menuList;
    }
}
