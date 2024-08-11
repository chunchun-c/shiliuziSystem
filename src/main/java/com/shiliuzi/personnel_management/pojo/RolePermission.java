package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("role_permission")
@Data
public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
}
