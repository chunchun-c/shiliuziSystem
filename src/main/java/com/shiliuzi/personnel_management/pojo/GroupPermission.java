package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("group_permission")
@Data
public class GroupPermission {
    private Integer id;
    private Integer groupId;
    private Integer permissionId;
}
