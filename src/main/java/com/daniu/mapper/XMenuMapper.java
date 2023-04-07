package com.daniu.mapper;

import com.daniu.entity.XMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
@Mapper
public interface XMenuMapper extends BaseMapper<XMenu> {

    List<XMenu> getMenuListByUserId(@Param("userId") Integer userId,@Param("parentId") Integer parentId);
}
