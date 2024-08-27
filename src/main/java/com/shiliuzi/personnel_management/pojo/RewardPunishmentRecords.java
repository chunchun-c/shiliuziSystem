package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
public class RewardPunishmentRecords {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String studentId;
    private String grade;
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
}
