package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    private Integer id;
    private String name;
    private String studentId;
    private String grade;
}
