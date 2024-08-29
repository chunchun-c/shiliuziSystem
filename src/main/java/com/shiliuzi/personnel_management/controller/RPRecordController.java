package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPRecordService;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RPRecordController {

    @Autowired
    RPRecordService rpRecordService;

    @TestPermission
    @GetMapping("/getRPRecord")
    public Result getAllRPRecord(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                 @RequestParam(value = "size",defaultValue = "10")Integer size,
                                 @RequestBody RPRecordsInfoVo rpRecordsInfoVo) {
        //构建模糊查询wrapper
        QueryWrapper<RPRecords> queryWrapper = new QueryWrapper<>();
        if (rpRecordsInfoVo != null){
            queryWrapper = (QueryWrapper<RPRecords>) rpRecordService.getSelectWrapper(rpRecordsInfoVo).getData();
        }


        Page<RPRecords> rpRecordsPage=new Page<>(pn,size);
        Page<RPRecords> page = rpRecordService.page(rpRecordsPage,queryWrapper);
        return Result.success(page);
    }


}
