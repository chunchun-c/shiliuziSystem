package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("permission")
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String path;
    private String comment;
}
