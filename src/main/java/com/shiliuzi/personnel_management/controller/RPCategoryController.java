package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.RPCategory;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class RPCategoryController {

    @Autowired
    RPCategoryService rpCategoryService;

    //获取奖惩类别信息
    @TestPermission
    @GetMapping("/getRPCategory")
    public Result getAllRPCategory(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                   @RequestParam(value = "size",defaultValue = "10")Integer size,
                                   @RequestParam Integer typeId) {
        QueryWrapper<RPCategory> queryWrapper = new QueryWrapper<>();
        if (typeId!=null && typeId>=1 && typeId<=2){
            queryWrapper.eq("type_id",typeId);
        }
        Page<RPCategory> rpCategoryPage=new Page<>(pn,size);
        Page<RPCategory> page = rpCategoryService.page(rpCategoryPage,queryWrapper);
        return Result.success(page);
    }

    //禁用或启用奖惩类别
    @TestPermission
    @GetMapping("/disOrEnRPCategory")
    public Result disOrEnRPCategory(@RequestParam Integer id,@RequestParam Integer active) {
        UpdateWrapper<RPCategory> updateWrapper = new UpdateWrapper<>();
        if (active!=null && active>=0 && active<=1){
            updateWrapper.eq("id", id);
            updateWrapper.set("is_enable", active);
        }else {
            return Result.fail("状态id错误");
        }
        boolean update = rpCategoryService.update(updateWrapper);
        if (update){
            return Result.success();
        }else {
            return Result.fail("禁用或启用失败");
        }
    }

    //添加奖惩类别
    @TestPermission
    @PostMapping("/addRPCategory")
    public Result addRPCategory(@RequestBody @Validated RPCategory.addRPCategory rpCategory) {
        boolean save = rpCategoryService.save(rpCategory);
        if (save){
            return Result.success(rpCategory.getId());
        }else {
            return Result.fail("保存失败");
        }
    }

    //修改奖惩类别
    @TestPermission
    @PostMapping("/updRPCategory")
    public Result updRPCategory(@RequestBody @Validated RPCategory.updRPCategory rpCategory) {
        if (rpCategoryService.getById(rpCategory.getId())==null) {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
        boolean update = rpCategoryService.updateById(rpCategory);
        if (update){
            return Result.success();
        }else {
            return Result.fail("更新失败");
        }

    }
}