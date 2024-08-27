package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色表（管理员，普通用户）
 */
@TableName("role")
@Data
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String comment;
}
