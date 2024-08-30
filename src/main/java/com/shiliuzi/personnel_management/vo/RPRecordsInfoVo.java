package com.shiliuzi.personnel_management.vo;

import lombok.Data;

//用于模糊查询
@Data
public class RPRecordsInfoVo {
    private String name;
    private String studentId;
    private Integer gradeId;
    private Integer groupId;
    private Integer roleId;
    private Integer rpTypeId;
}
