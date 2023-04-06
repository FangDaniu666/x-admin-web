package com.daniu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daniu.entity.Imgs;
import com.daniu.service.impl.ImgsServiceImpl;
import com.daniu.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 图片控制器
 * </p>
 *
 * @author FangDaniu
 * @since 2023-03-31
 */
@Api(tags = "图片接口列表")
@RestController
@RequestMapping("/imgs")
public class ImgsController {

    @Autowired
    private ImgsServiceImpl imgsService;

    @GetMapping("/{id}")
    public Result<Imgs> getImgById(@PathVariable(value = "id") Integer id) {
        Imgs imgs = imgsService.getById(id);
        return Result.success(imgs);
    }

    @GetMapping("/all")
    public Result<Map<String, Object>> getAllImgs(@RequestParam(value = "pageNo") Integer pageNo,
                                                  @RequestParam(value = "orderBy") Integer orderBy) {
        LambdaQueryWrapper<Imgs> wrapper = new LambdaQueryWrapper<>();
        if (orderBy == 0) {
            wrapper.orderByDesc(Imgs::getId);
//            System.out.println("orderByDesc");
        }
        IPage page = new Page(pageNo, 30);
        imgsService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }

}
