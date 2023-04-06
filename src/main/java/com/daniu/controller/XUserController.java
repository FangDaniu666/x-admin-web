package com.daniu.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.entity.XUser;
import com.daniu.service.IXUserService;
import com.daniu.utils.JwtUtils;
import com.daniu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
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
 * @since 2023-03-26
 */
@Api(tags = "用户接口列表")
@RestController
@RequestMapping("/user")
public class XUserController {
    @Autowired
    private IXUserService ixUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 得到所有xusers
     *
     * @return {@link List}<{@link XUser}>
     */
    @GetMapping("/all")
    public Result<List<XUser>> getAllXUsers() {
        List<XUser> results = ixUserService.list();
        return Result.success(results);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> userLogin(@RequestBody XUser user) {
        Map<String, Object> data = ixUserService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        // 根据token获取用户信息,redis
        Map<String, Object> data = ixUserService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "登录信息无效,请重新登录");
    }

    @PostMapping("/logout")
    public Result<?> userLogout(@RequestHeader("X-Token") String token) {
        ixUserService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                               @RequestParam(value = "phone", required = false) String phone,
                                               @RequestParam(value = "pageNo") Integer pageNo) {
        LambdaQueryWrapper<XUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),XUser::getUsername, username);
        wrapper.eq(StringUtils.hasLength(phone),XUser::getPhone, phone);
        IPage page = new Page(pageNo, 10);
        ixUserService.page(page,wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    @PostMapping("/adduser")
    public Result<?> addUser(@RequestBody XUser user) {
        //添加用户
        //对password进行MD5加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean save = ixUserService.addUser(user);
        if (save) {
            return Result.success(save);
        }
        System.out.println(user);
        return Result.fail(20004, "添加失败");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody XUser user) {
        ixUserService.updateUser(user);
        user.setPassword(null);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<XUser> getUserById(@PathVariable(value = "id") Integer id) {
        XUser user = ixUserService.getUserById(id);
        user.setPassword(null);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<XUser> deleteUser(@PathVariable(value = "id") Integer id) {
        ixUserService.deleteUser(id);
        return Result.success();
    }
}

















