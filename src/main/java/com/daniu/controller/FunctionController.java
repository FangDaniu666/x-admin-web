package com.daniu.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.entity.XUser;
import com.daniu.service.IXUserService;
import com.daniu.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <p>
 * 功能控制器
 * </p>
 *
 * @author FangDaniu
 * @since 2023-03-26
 */
@Api(tags = "功能接口列表")
@RestController
@RequestMapping("/function")
public class FunctionController {
    @Autowired
    private IXUserService ixUserService;

    @GetMapping("/getQrCode")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "url") String url, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        QrCodeUtil.generate(url, 300, 300, "jpg", response.getOutputStream());
        response.flushBuffer();
        response.getOutputStream().close();
        return Result.success();
    }

    @GetMapping("/all")
    public Result<List<XUser>> getAllXUsers() {
        List<XUser> results = ixUserService.list();
        return Result.success(results);
    }
}