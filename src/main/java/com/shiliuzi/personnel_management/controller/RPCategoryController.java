package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.pojo.RPCategory;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RPCategoryController {

    @Autowired
    RPCategoryService rpCategoryService;

    //获取奖惩类别信息
    @TestPermission
    @GetMapping("/getRPCategory")
    public Result getAllRPCategory(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                   @RequestParam(value = "size",defaultValue = "10")Integer size,
                                   @RequestParam Integer type) {
        QueryWrapper<RPCategory> queryWrapper = new QueryWrapper<>();
        if (type!=null && type>=1 && type<=2){
            queryWrapper.eq("type",type);
        }
        Page<RPCategory> rpCategoryPage=new Page<>(pn,size);
        Page<RPCategory> page = rpCategoryService.page(rpCategoryPage,queryWrapper);
        return Result.success(page);
    }
}