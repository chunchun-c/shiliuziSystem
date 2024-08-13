package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_group")
@Data
public class UserGroup {
    private Integer id;
    private Integer userId;
    private Integer groupId;
}
