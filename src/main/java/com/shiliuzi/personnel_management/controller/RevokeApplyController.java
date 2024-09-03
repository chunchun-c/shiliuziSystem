package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RevokeApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @TestPermission
    @GetMapping("/getApplyRevoke")
    public Result getApplyRevoke(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return revokeApplyService.getAllRevokeApply(pn, size);
    }


    //删除撤销申请消息表
    @TestPermission
    @GetMapping("/deleteApplyRevoke")
    public Result deleteApplyRevoke(@RequestParam Integer id
    ) {

        boolean remove = revokeApplyService.removeById(id);
        if(remove){
            return Result.success("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }

    //同意撤销申请消息
    @TestPermission
    @GetMapping("/allowApplyRevoke")
    public Result allowApplyRevoke(@RequestParam Integer id
    ) {
        return revokeApplyService.allow(id);

    }

    //忽略撤销申请消息
    @TestPermission
    @GetMapping("/ignoreApplyRevoke")
    public Result ignoreApplyRevoke(@RequestParam Integer id
    ) {
        return revokeApplyService.ignore(id);

    }
}
