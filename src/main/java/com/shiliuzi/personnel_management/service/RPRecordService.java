package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;

public interface RPRecordService extends IService<RPRecords> {

    Result getSelectWrapper(RPRecordsInfoVo rpRecordsInfoVo);

    //导出excel
    void exportRPRecord(HttpServletResponse response);

    Result revoke(RPRecords.RevokeRecord revokeRecord);
}
