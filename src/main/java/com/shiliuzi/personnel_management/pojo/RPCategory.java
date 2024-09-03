package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.*;
import lombok.Data;

@TableName("reward_punishment_category")
@Data
public class RPCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer typeId;
    private String name;
    private Integer isEnable;
    private String comment;

    //修改奖惩类别
    @Data
    public static class updRPCategory extends RPCategory {
        @NotNull
        private Integer id;

        @Min(1)
        @Max(2)
        private Integer typeId;

        @Size(min = 0, max = 20)
        private String name;

        @Min(0)
        @Max(1)
        private Integer isEnable;

        @Size(min = 0, max = 20)
        private String comment;
    }

    //新增奖惩类别
    @Data
    public static class addRPCategory extends RPCategory {
        @Null
        private Integer id;

        @Min(1)
        @Max(2)
        @NotNull
        private Integer typeId;

        @NotBlank
        @Size(min = 1, max = 20)
        private String name;

        @NotNull
        @Min(0)
        @Max(1)
        private Integer isEnable;

        @Size(min = 0, max = 20)
        private String comment;
    }
}
