package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 奖惩记录表
 * 姓名，name
 * 学号，studentId
 * 年级，grade
 * 组别，group
 * 担任角色，role（管理员，普通用户）
 * 奖惩类型，rpType（奖励，惩罚）
 * 奖惩类别，rpCategory（迟到，旷课……）
 * 奖惩日期，rpDate
 * 奖惩内容，rpContent
 * 奖惩金额，rpAmount
 * 奖惩原因，rpReason
 * 奖惩备注，rpComment
 * 是否撤销，isRevoke（0未撤销，1已撤销）
 * 撤销日期，revokeDate
 * 撤销原因，revokeReason
 * 撤销备注，revokeComment
 * 操作人，operatorId
 * 最近一次更新时间，recentUpdateDate
 * 操作，operate（撤销、编辑、删除）
 */
@TableName("reward_punishment_records")
@Data
public class RPRecords {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String studentId;
    private String grade;
    @TableField("`group`")
    private String group;
    private String role;
    private String rpType;
    private String rpCategory;
    private LocalDateTime rpDate;
    private String rpContent;
    private BigDecimal rpAmount;
    private String rpReason;
    private String rpComment;
    private Integer isRevoke;
    private LocalDateTime revokeDate;
    private String revokeReason;
    private String revokeComment;
    private Integer operatorId;
    private LocalDateTime recentUpdateDate;
    private String operate;

    //增加或修改
    @Data
    public static class AURPRecords {
        private Integer id;
        private String name;
        private String studentId;
        private Integer gradeId;
        private Integer groupId;
        private Integer roleId;
        private Integer rpTypeId;
        private Integer rpCategoryId;
        private LocalDateTime rpDate;
        private String rpContent;
        private BigDecimal rpAmount;
        private String rpReason;
        private String rpComment;
    }

    //增加奖惩记录json
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class addRPRecords extends AURPRecords{
        @Null
        private Integer id;

        @NotBlank
        @Size(min = 1,max = 10)
        private String name;

        @Pattern(regexp = "^\\d{10}$")
        private String studentId;

        @Min(1)
        @Max(4)
        private Integer gradeId;

        @Min(1)
        private Integer groupId;

        @Max(2)
        @Min(1)
        private Integer roleId;

        @NotNull
        @Min(1)
        @Max(2)
        private Integer rpTypeId;

        @NotNull
        @Min(1)
        private Integer rpCategoryId;

        @NotNull
        @Past
        private LocalDateTime rpDate;

        @NotBlank
        @Size(min = 1,max = 50)
        private String rpContent;

        private BigDecimal rpAmount;

        @NotBlank
        @Size(min = 1,max = 50)
        private String rpReason;

        @Size(min = 1,max = 50)
        private String rpComment;
    }

    //修改奖惩记录json
    @Data
    public static class updRPRecords extends AURPRecords {

        @NotNull
        private Integer id;

        @Size(min = 0,max = 10)
        private String name;

        @Pattern(regexp = "^\\d{10}$")
        private String studentId;

        @Min(1)
        @Max(4)
        private Integer gradeId;

        @Min(1)
        private Integer groupId;

        @Max(2)
        @Min(1)
        private Integer roleId;

        @Min(1)
        @Max(2)
        private Integer rpTypeId;

        @Min(1)
        private Integer rpCategoryId;

        @Past
        private LocalDateTime rpDate;

        @Size(min = 0,max = 50)
        private String rpContent;

        private BigDecimal rpAmount;

        @Size(min = 0,max = 50)
        private String rpReason;

        @Size(min = 0,max = 50)
        private String rpComment;
    }


    //撤销时前端传回的json
    @Data
    public static class RevokeRecord {
        private Integer id;
        @NotBlank(message = "撤销原因不能为空")
        private String revokeReason;
        @NotBlank(message = "撤销备注不能为空")
        private String revokeComment;
    }
}
