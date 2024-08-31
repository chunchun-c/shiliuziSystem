package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("reward_punishment_category")
@Data
public class RPCategory {
    private Integer id;
    private Integer typeId;
    private String name;
    private Integer isEnable;
    private String comment;
}
