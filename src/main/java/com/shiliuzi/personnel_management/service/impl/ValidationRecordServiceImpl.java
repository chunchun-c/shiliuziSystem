package com.shiliuzi.personnel_management.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.listener.ExcelListener;
import com.shiliuzi.personnel_management.mapper.ValidationRecordMapper;
import com.shiliuzi.personnel_management.pojo.ValidationRecord;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.ValidationRecordService;
import com.shiliuzi.personnel_management.utils.ValidationUtil;
import com.shiliuzi.personnel_management.vo.VRExcelVo;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: chun
 **/

@Service
public class ValidationRecordServiceImpl extends ServiceImpl<ValidationRecordMapper, ValidationRecord> implements ValidationRecordService {

    @Autowired
    ValidationRecordMapper validationRecordMapper;

    @Override
    public Result validateExcel(MultipartFile file, Integer adminId) {
        List<ValidationRecord.Excel> list;
        List<ValidationRecord.Excel> fail = new ArrayList<>();
        VRExcelVo vrExcelVo = new VRExcelVo();
        try {
            //利用easyExcel读取其中消息，再取出校验器做校验
            list = EasyExcel.read(file.getInputStream(), ValidationRecord.Excel.class, new ExcelListener()).sheet().doReadSync();
            list.forEach(data -> {
                Set<ConstraintViolation<ValidationRecord.Excel>> violations = ValidationUtil.getValidator().validate(data);
                if (violations.size() > 0) {
                    fail.add(data);
                }
            });

            vrExcelVo.setFail(fail);
            list.removeAll(fail);
            vrExcelVo.setSuccess(list);

            // 获取成功和失败的次数
            int failSize = fail.size();
            int successSize = list.size();

            // 插入失败记录到数据库
            Date now = new Date();
            for (int i = 0; i < failSize; i++) {
                LocalDateTime localDateTime = ZonedDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).toLocalDateTime();
                ValidationRecord failVR = new ValidationRecord(adminId, "0", localDateTime);
                validationRecordMapper.insert(failVR);
            }

            // 插入成功记录到数据库
            for (int i = 0; i < successSize; i++) {
                LocalDateTime localDateTime = ZonedDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).toLocalDateTime();
                ValidationRecord successVR = new ValidationRecord(adminId, "1", localDateTime);
                validationRecordMapper.insert(successVR);
            }
        } catch (IOException e) {
            throw new AppException(AppExceptionCodeMsg.EXCEL_DOWNLOAD_ERROR);
        }



        return Result.success("导入操作成功",vrExcelVo);


    }
}
