package com.daniu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.entity.XRole;
import com.daniu.entity.XRole;
import com.daniu.entity.XUser;
import com.daniu.service.IXRoleService;
import com.daniu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author FangDaniu
 * @since 2023-04-04
 */
@Api(tags = "角色接口列表")
@RestController
@RequestMapping("/role")
public class XRoleController {
    @Autowired
    private IXRoleService ixRoleService;

    @GetMapping("/list")
    public Result<Map<String, Object>> getRoleList(
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "pageNo") Integer pageNo) {
        LambdaQueryWrapper<XRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(roleName), XRole::getRoleName, roleName);
        IPage page = new Page(pageNo, 10);
        ixRoleService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }

    @PostMapping("/addrole")
    public Result<?> addRole(@RequestBody XRole role) {
        boolean save = ixRoleService.addRole(role);
        if (save) {
            return Result.success(save);
        }
        return Result.fail(20004, "添加失败");
    }

    @PutMapping
    public Result<?> updateRole(@RequestBody XRole role) {
        ixRoleService.updateRole(role);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<XRole> getRoleById(@PathVariable(value = "id") Integer id) {
        XRole role = ixRoleService.getRoleById(id);
        return Result.success(role);
    }

    @DeleteMapping("/{id}")
    public Result<XRole> deleteRole(@PathVariable(value = "id") Integer id) {
        ixRoleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<XRole>> getAllRole() {
        List<XRole> roleList = ixRoleService.list();
        return Result.success(roleList);
    }
}
