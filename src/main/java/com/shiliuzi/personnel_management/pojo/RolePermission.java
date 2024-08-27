package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_permission")
@Data
public class RolePermission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
