package com.shiliuzi.personnel_management.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.GroupMapper;
import com.shiliuzi.personnel_management.mapper.RPRecordMapper;
import com.shiliuzi.personnel_management.mapper.RoleMapper;
import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.pojo.Role;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPRecordService;
import com.shiliuzi.personnel_management.utils.ExcelUtil;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RPRecordServiceImpl extends ServiceImpl<RPRecordMapper, RPRecords> implements RPRecordService {

    @Autowired
    RPRecordMapper rpRecordMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    RoleMapper roleMapper;


    @Override
    public Result getSelectWrapper(RPRecordsInfoVo rpRecordsInfoVo) {
        //构建模糊查询wrapper
        QueryWrapper<RPRecords> queryWrapper = new QueryWrapper<>();
        if (rpRecordsInfoVo.getName() != null && !rpRecordsInfoVo.getName().isEmpty()) {
            queryWrapper.like("name", rpRecordsInfoVo.getName());
        }
        if (rpRecordsInfoVo.getStudentId() != null && !rpRecordsInfoVo.getStudentId().isEmpty()) {
            queryWrapper.like("student_id", rpRecordsInfoVo.getStudentId());
        }
        if (rpRecordsInfoVo.getGradeId() !=null){
            String[] grades = {"大一", "大二", "大三", "大四"};
            queryWrapper.eq("grade", grades[rpRecordsInfoVo.getGradeId()-1]);
        }
        if (rpRecordsInfoVo.getGroupId() != null){
            Group group = groupMapper.selectById(rpRecordsInfoVo.getGroupId());
            queryWrapper.eq("`group`", group.getName());
        }
        if (rpRecordsInfoVo.getRoleId() !=null){
            Role role = roleMapper.selectById(rpRecordsInfoVo.getRoleId());
            queryWrapper.eq("role", role.getName());
        }
        if (rpRecordsInfoVo.getRpTypeId() != null){
            if (rpRecordsInfoVo.getRpTypeId() == 1){
                queryWrapper.eq("rp_type", "奖励");
            }else if (rpRecordsInfoVo.getRpTypeId() == 2){
                queryWrapper.eq("rp_type","惩罚");
            }
        }
        return Result.success(queryWrapper);
    }

    @Override
    public void exportRPRecord(HttpServletResponse response) {
        List<RPRecords> rpRecords = rpRecordMapper.selectList(null);

        try {
            Map<String, Object> param = new HashMap<>();
            param.put("title", "奖惩记录模板");
            param.put("list", rpRecords);
            ExcelUtil.downLoadExcel("奖惩记录模板", "奖惩记录.xlsx", param, response);

        } catch (Exception e) {
            throw new AppException(AppExceptionCodeMsg.EXCEL_EXPORT_ERROR);
        }

    }

    @Override
    public Result revoke(RPRecords.RevokeRecord revokeRecord) {
        //先通过id获取该记录
        Integer id = revokeRecord.getId();
        QueryWrapper<RPRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("is_revoke").eq("id", id);

        // 执行查询，返回一个 Map 对象
        Map<String, Object> resultMap = (Map<String, Object>) rpRecordMapper.selectMaps(queryWrapper);

        //判断是否存在
        if (resultMap.isEmpty()) {
            return Result.fail("记录未找到");
        }

        //判断是否已经撤销
        Integer isRevoke = (Integer) resultMap.get("is_revoke");
        if(isRevoke == 1){
            return Result.success("记录已成功撤销");
        }

        // 创建更新对象
        RPRecords updateRecord = new RPRecords();
        updateRecord.setId(id);
        updateRecord.setIsRevoke(1);
        updateRecord.setRevokeReason(revokeRecord.getRevokeReason());
        updateRecord.setRevokeComment(revokeRecord.getRevokeComment());
        updateRecord.setRevokeDate(LocalDateTime.now());

        // 执行更新操作
        int updateSuccess = rpRecordMapper.updateById(updateRecord);
        // 判断更新是否成功
        if (updateSuccess > 0) {
            return Result.success("记录已成功撤销");
        } else {
            return Result.fail("记录更新失败");
        }
    }


}
