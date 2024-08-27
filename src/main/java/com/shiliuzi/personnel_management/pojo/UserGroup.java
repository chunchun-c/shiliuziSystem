package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_group")
@Data
public class UserGroup {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer groupId;
}
