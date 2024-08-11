package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_role")
@Data
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
