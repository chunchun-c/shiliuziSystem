package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shiliuzi.personnel_management.validate.group.RegisterModel;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "注册时姓名不能为空")
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

    //注册时前端传回的json
    @Data
    public static class RegisterUser {
        private String name;
        private String studentId;
        private Integer gradeId;
        private String password;
        private Integer groupId;
        private Integer roleId;
    }
}
