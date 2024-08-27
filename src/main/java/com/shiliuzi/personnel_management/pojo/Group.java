package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 组别表（前端后台）
 */
@TableName("group")
@Data
public class Group {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String comment;
}
