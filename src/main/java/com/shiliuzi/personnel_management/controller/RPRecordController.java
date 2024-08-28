package com.shiliuzi.personnel_management.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RPRecordController {

    @Autowired
    RPRecordService rpRecordService;

    @GetMapping("/getAllRPRecord")
    public Result getAllRPRecord(@RequestParam(value = "pn",defaultValue = "1")Integer pn, @RequestParam(value = "size",defaultValue = "10")Integer size) {
        Page<RPRecords> rpRecordsPage=new Page<>(pn,size);
        Page<RPRecords> page = rpRecordService.page(rpRecordsPage);
        return Result.success(page);
    }
}
