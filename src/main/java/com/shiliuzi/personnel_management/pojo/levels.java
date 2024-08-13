package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 上下级关系表
 * 存储上级用户id和下级用户id
 */
@TableName("levels")
@Data
public class levels {
    private Integer id;
    private Integer upperId;
    private Integer lowerId;
}
