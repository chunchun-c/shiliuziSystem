package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.pojo.RPCategory;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.RPCategoryService;
import com.shiliuzi.personnel_management.service.RPRecordService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Validated
public class RPRecordController {

    @Autowired
    RPRecordService rpRecordService;

    @Autowired
    RPCategoryService rpCategoryService;

    @Autowired
    GroupService groupService;

    @Autowired
    RoleService roleService;

    //获取奖惩记录
    @TestPermission
    @GetMapping("/getRPRecord")
    public Result getAllRPRecord(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                 @RequestBody RPRecordsInfoVo rpRecordsInfoVo) {
        //构建模糊查询wrapper
        QueryWrapper<RPRecords> queryWrapper = new QueryWrapper<>();
        if (rpRecordsInfoVo != null) {
            queryWrapper = (QueryWrapper<RPRecords>) rpRecordService.getSelectWrapper(rpRecordsInfoVo).getData();
        }

        Page<RPRecords> rpRecordsPage = new Page<>(pn, size);
        Page<RPRecords> page = rpRecordService.page(rpRecordsPage, queryWrapper);
        return Result.success(page);
    }

    //删除奖惩记录
    @TestPermission
    @GetMapping("/delRPRecord")
    public Result delRPRecord(@RequestParam Integer id) {
        boolean remove = rpRecordService.removeById(id);
        if (remove) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    //新增奖惩记录
    @TestPermission
    @GetMapping("/addRPRecord")
    public Result addRPRecord(@RequestBody @Validated RPRecords.addRPRecords addRPRecords) {

        //判断groupId是否合法
        if (addRPRecords.getGroupId() > groupService.list().size()) {
            return Result.fail("组别id错误");
        }

        //判断奖惩类别、类型
        RPCategory rpCategory = rpCategoryService.getById(addRPRecords.getRpCategoryId());
        if (rpCategory == null || rpCategory.getIsEnable() != 1) {
            return Result.fail("奖惩类别错误");
        }
        if (!Objects.equals(rpCategory.getTypeId(), addRPRecords.getRpTypeId())) {
            return Result.fail("奖惩类型与类别不匹配");
        }

        //新增数据
        return rpRecordService.addRPRecord(addRPRecords);
    }

    //导出奖惩记录
    @PostMapping(value = "/exportRPRecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result exportRPRecord(HttpServletResponse response) {
        rpRecordService.exportRPRecord(response);
        return Result.success("导出成功");
    }


    //管理员撤销奖惩记录
    @PostMapping ("/revokeRPRecord")
    public Result revokeRPRecord(@Validated @RequestBody RPRecords.RevokeRecord revokeRecord ) {

        Result revoke = rpRecordService.revoke(revokeRecord);

        return revoke;
    }

}
