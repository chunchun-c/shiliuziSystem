package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("permission")
@Data
public class Permission {
    private Integer id;
    private String name;
    private String path;
    private String comment;
}
