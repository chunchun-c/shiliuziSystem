package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RPRecordService extends IService<RPRecords> {

    //构建模糊查询wrapper
    Result getSelectWrapper(RPRecordsInfoVo rpRecordsInfoVo);

    //新增奖惩记录
    Result addRPRecord(RPRecords.addRPRecords addRPRecords);

    //修改奖惩记录
    Result updRPRecord(RPRecords.updRPRecords updRPRecords);

    //导出excel
    void exportRPRecord(HttpServletResponse response, String studentId);

    Result addRPRecordAnnex(MultipartFile file,Integer reRecordsId);
    Result revoke(RPRecords.RevokeRecord revokeRecord);

    void importRPRecord(MultipartFile file);
}
