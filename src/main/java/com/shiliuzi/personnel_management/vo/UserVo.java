package com.shiliuzi.personnel_management.vo;

import lombok.Data;

//返回前端的成员信息
@Data
public class UserVo {
    private String name;
    private String studentId;
    private String grade;
    private String group;
    private String role;
}
