package com.daniu.controller;

import com.daniu.entity.XMenu;
import com.daniu.service.IXMenuService;
import com.daniu.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
@Api(tags = "权限接口列表")
@RestController
@RequestMapping("/menu")
public class XMenuController {
    @Autowired
    private IXMenuService menuService;

    @GetMapping("/list")
    public Result<List<XMenu>> getAllMenu() {
        List<XMenu> menuList = menuService.getAllMenu();
        return Result.success(menuList);
    }
}
