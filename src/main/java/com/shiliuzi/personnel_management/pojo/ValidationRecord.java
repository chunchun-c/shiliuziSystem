package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: excel文件验证信息
 * @author: chun
 **/

@Data

@TableName("validation_record")
public class ValidationRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer admin_id;
    //1为验证成功，0为验证失败
    private String state;
    private LocalDateTime date;

    public ValidationRecord(Integer admin_id, String state, LocalDateTime date) {
        this.admin_id = admin_id;
        this.state = state;
        this.date = date;
    }

    //导入excel时传来的对象
    @Data
    public static class Excel {
        @Size(min = 1,max = 10,message = "名字格式错误")
        private String name;

        @Pattern(regexp = "^\\d{10}$",message = "学号格式错误")
        private String studentId;


        @NotNull(message = "奖惩类型不能为空")
        @Min(value = 1,message = "奖惩类型格式错误")
        @Max(value = 2,message = "奖惩类型格式错误")
        private Integer rpTypeId;

        @NotNull(message = "奖惩类别不能为空")
        @Min(value = 1,message = "奖惩类别格式错误")
        private Integer rpCategoryId;

        @NotNull(message = "奖惩日期不能为空")
        @Past(message = "奖惩日期没有早于当前")
        private LocalDateTime rpDate;

        @NotBlank(message = "奖惩内容不能为空")
        @Size(min = 1,max = 50,message = "奖惩内容格式错误")
        private String rpContent;

        private BigDecimal rpAmount;

        @NotBlank(message = "奖惩原因不能为空")
        @Size(min = 1,max = 50,message = "奖惩原因格式错误")
        private String rpReason;

        @NotBlank(message = "奖惩备注不能为空")
        @Size(min = 1,max = 50,message = "奖惩备注格式错误")
        private String rpComment;
    }
}
