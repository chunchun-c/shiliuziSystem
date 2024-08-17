package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 用户表
 * 姓名，name
 * 学号，studentId(固定10位数字）
 * 年级,grade
 * 存入session时增加权限列表
 */
@TableName("user")
@Data
public class User {
    private Integer id;
    private String name;
    private String studentId;
    private String grade;
    private String password;
    @TableField(exist = false)
    private List<Permission> permissionList;
}
