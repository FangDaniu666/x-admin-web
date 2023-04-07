package com.daniu.service;

import com.daniu.entity.XMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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

    List<XMenu> getMenuListByUserId(Integer userId);

}
