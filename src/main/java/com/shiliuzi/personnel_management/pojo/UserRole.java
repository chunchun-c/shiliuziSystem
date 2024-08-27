package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_role")
@Data
public class UserRole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
