package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.pojo.RevokeApply;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RevokeApplyService;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 撤销奖惩记录的申请记录
 * @author: chun
 **/

@RestController
public class RevokeApplyController {
    @Autowired
    RevokeApplyService revokeApplyService;

    //获取撤销申请消息表
    //@TestPermission
    @GetMapping("/getApplyRevoke")
    public Result getApplyRevoke(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {



        return Result.success(revokeApplyService.getAllRevokeApply(pn,size));
    }
}
