package com.shiliuzi.personnel_management.vo;

import com.shiliuzi.personnel_management.pojo.ValidationRecord;
import lombok.Data;

import java.util.List;

/**
 * @description: 将失败和成功的excel消息包装传给前端
 * @author: chun
 **/

@Data
public class VRExcelVo {

    //成功列表
    private List<ValidationRecord.Excel> success;
    //失败列表
    private List<ValidationRecord.Excel> fail;

}
