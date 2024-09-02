package com.shiliuzi.personnel_management.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.GroupMapper;
import com.shiliuzi.personnel_management.mapper.RPCategoryMapper;
import com.shiliuzi.personnel_management.mapper.RPRecordMapper;
import com.shiliuzi.personnel_management.mapper.RoleMapper;
import com.shiliuzi.personnel_management.pojo.Group;
import com.shiliuzi.personnel_management.pojo.RPCategory;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.pojo.Role;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RPRecordService;
import com.shiliuzi.personnel_management.utils.ExcelUtil;
import com.shiliuzi.personnel_management.utils.ThreadLocalUtil;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg.REVOKE_INFO_NOT_FOUND;

@Service
public class RPRecordServiceImpl extends ServiceImpl<RPRecordMapper, RPRecords> implements RPRecordService {

    @Autowired
    RPRecordMapper rpRecordMapper;

    @Autowired
    RPCategoryMapper rpCategoryMapper;

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

    //读取文件保存路径
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Result addRPRecord(RPRecords.addRPRecords addRPRecords) {

        //创建奖惩记录对象
        RPRecords rpRecords = new RPRecords();
        rpRecords.setName(addRPRecords.getName());
        rpRecords.setStudentId(addRPRecords.getStudentId());

        if (addRPRecords.getGradeId()!=null){
            String[] grades = {"大一", "大二", "大三", "大四"};
            rpRecords.setGrade(grades[addRPRecords.getGradeId()-1]);
        }
        if (addRPRecords.getGroupId()!=null){
            Group group = groupMapper.selectById(addRPRecords.getGroupId());
            rpRecords.setGroup(group.getName());
        }
        if (addRPRecords.getRoleId()!=null){
            Role role = roleMapper.selectById(addRPRecords.getRoleId());
            rpRecords.setRole(role.getName());
        }

        String[] rpType = {"奖励", "惩罚"};
        rpRecords.setRpType(rpType[addRPRecords.getRpTypeId()-1]);

        RPCategory rpCategory = rpCategoryMapper.selectById(addRPRecords.getRpCategoryId());
        rpRecords.setRpCategory(rpCategory.getName());

        rpRecords.setRpDate(addRPRecords.getRpDate());
        rpRecords.setRpContent(addRPRecords.getRpContent());
        rpRecords.setRpAmount(addRPRecords.getRpAmount());
        rpRecords.setRpReason(addRPRecords.getRpReason());
        rpRecords.setRpComment(addRPRecords.getRpComment());

        //添加操作人
        rpRecords.setOperatorId(ThreadLocalUtil.getUser().getId());
        //添加更新日期
        rpRecords.setRecentUpdateDate(LocalDateTime.now());
        int insert = rpRecordMapper.insert(rpRecords);

        if (insert == 1){
            return Result.success(rpRecords.getId());
        }else {
            return Result.fail("新增失败");
        }
    }

    private boolean isValidFileType(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return Arrays.asList("xls", "ppt", "docx", "xlsx", "pptx", "pdf").contains(fileExtension);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex > 0 ? fileName.substring(dotIndex + 1).toLowerCase() : "";
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

        // 执行查询
        List<Map<String, Object>> maps = rpRecordMapper.selectMaps(queryWrapper);

        //判断是否存在
        if (maps.isEmpty()) {
            //未找到该记录就抛异常
            throw new AppException(REVOKE_INFO_NOT_FOUND);
        }

        //判断是否已经撤销
        Integer isRevoke = (Integer) maps.get(0).get("is_revoke");
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

    @Override
    public Result addRPRecordAnnex(MultipartFile file,Integer reRecordsId) {
        //附件格式校验
        if (file!=null){
            String fileName = file.getOriginalFilename();
            if (fileName == null || !isValidFileType(fileName)) {
                return Result.fail("附件格式错误,允许格式为xls、ppt、docx、xlsx、pptx、pdf");
            }
        }
        //保存附件
        String fileName = file.getOriginalFilename();
        fileName = "奖惩记录"+reRecordsId+"附件"+"."+getFileExtension(fileName);
        try {
            File destinationFile = new File(uploadDir,fileName);
            file.transferTo(destinationFile);
        } catch (IOException e) {
            return Result.fail("附件保存失败");
        }
        return Result.success();
    }

}
