package com.daniu.service;

import com.daniu.entity.XMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
public interface IXMenuService extends IService<XMenu> {

    List<XMenu> getAllMenu();
}
