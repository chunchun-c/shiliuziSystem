package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.vo.RPRecordsInfoVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RPRecordService extends IService<RPRecords> {

    Result getSelectWrapper(RPRecordsInfoVo rpRecordsInfoVo);

    Result addRPRecord(RPRecords.addRPRecords addRPRecords);

    //导出excel
    void exportRPRecord(HttpServletResponse response);

    Result addRPRecordAnnex(MultipartFile file,Integer reRecordsId);
}
