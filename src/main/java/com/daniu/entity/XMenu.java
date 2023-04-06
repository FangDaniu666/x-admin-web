package com.daniu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
@TableName("x_menu")
@Data
public class XMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    private String component;

    private String path;

    private String redirect;

    private String name;

    private String title;

    private String icon;

    @TableField("parentId")
    private Integer parentId;

    @TableField("isLeaf")
    private String isLeaf;

    private Boolean hidden;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<XMenu> children;

    @TableField(exist = false)
    private Map<String, Object> meta;
    public Map<String, Object> getMeta(){
        meta =new HashMap<>();
        meta.put("title", title);
        meta.put("icon", icon);
        return meta;
    }
}
