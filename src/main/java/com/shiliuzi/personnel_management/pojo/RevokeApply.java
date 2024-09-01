package com.shiliuzi.personnel_management.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: 撤销申请记录
 * @author: chun
 **/

@TableName("revoke_apply")
@Data
public class RevokeApply {
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
    private LocalDateTime applyDate;
    //0未处理，1处理为同意，2处理为忽略
    private Integer state;

}
