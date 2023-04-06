package com.daniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daniu.entity.XRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-05
 */
@Mapper
public interface XRoleMenuMapper extends BaseMapper<XRoleMenu> {
    List<Integer> getMenuIdListByRoleId(@Param("roleId") Integer roleId);
}
