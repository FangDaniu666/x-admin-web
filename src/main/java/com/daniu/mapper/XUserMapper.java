package com.daniu.mapper;

import com.daniu.entity.XUser;
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
 * @since 2023-03-26
 */
@Mapper
public interface XUserMapper extends BaseMapper<XUser> {

    List<String> getRoleNameByUserId(@Param("id") Integer id);
}
