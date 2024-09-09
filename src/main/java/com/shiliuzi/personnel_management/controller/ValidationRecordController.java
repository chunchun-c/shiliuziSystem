package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.annotation.TestPermission;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.ValidationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: chun
 **/

@RestController
public class ValidationRecordController {
    @Autowired
    ValidationRecordService validationRecordService;


    //导入excel并校验数据
    //@TestPermission
    @PostMapping("/import")
    Result importExcel(@RequestParam("file") MultipartFile file, Integer adminId){
        return validationRecordService.validateExcel(file,adminId);
    }
}
