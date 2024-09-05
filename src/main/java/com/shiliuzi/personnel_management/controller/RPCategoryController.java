package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.RPCategory;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPCategoryService;
import com.shiliuzi.personnel_management.service.RPRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
public class RPCategoryController {

    @Autowired
    RPCategoryService rpCategoryService;

    @Autowired
    RPRecordService rpRecordService;

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

    //删除并替换奖惩类别
    @TestPermission
    @GetMapping("/delRPCategory")
    public Result delRPCategory(@RequestParam Integer delId,@RequestParam Integer updId) {
        if (Objects.equals(delId, updId) || rpCategoryService.getById(delId)==null || rpCategoryService.getById(updId)==null) {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
        String delName = rpCategoryService.getById(delId).getName();
        String updName = rpCategoryService.getById(updId).getName();

        boolean del = rpCategoryService.removeById(delId);
        if (!del){
            return Result.fail("删除失败");
        }
        UpdateWrapper<RPRecords> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("rp_category",delName).set("rp_category",updName);
        rpRecordService.update(updateWrapper);
        return Result.success();
    }
}