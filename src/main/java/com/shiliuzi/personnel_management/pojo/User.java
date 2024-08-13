package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表
 * 姓名，name
 * 学号，studentId(固定10位数字）
 * 年级,grade
 */
@TableName("user")
@Data
public class User {
    private Integer id;
    private String name;
    private String studentId;
    private String grade;
}
