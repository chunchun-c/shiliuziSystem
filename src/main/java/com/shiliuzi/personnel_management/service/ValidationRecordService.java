package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.ValidationRecord;
import com.shiliuzi.personnel_management.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface ValidationRecordService extends IService<ValidationRecord> {
    Result validateExcel(MultipartFile file, Integer adminId);
}
