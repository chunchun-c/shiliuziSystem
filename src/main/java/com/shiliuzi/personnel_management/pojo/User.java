package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String studentId;
    private String grade;
    private String password;
    @TableField(exist = false)
    private List<Permission> permissionList;

    public User() {
    }

    public User(String name, String studentId, Integer gradeId, String password) {
        this.name=name;
        this.studentId=studentId;
        switch (gradeId){
            case 1:
                this.grade="大一";
                break;
            case 2:
                this.grade="大二";
                break;
            case 3:
                this.grade="大三";
                break;
            case 4:
                this.grade="大四";
                break;
        }
        this.password=password;
    }
}
