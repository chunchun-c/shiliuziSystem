package com.shiliuzi.personnel_management.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.pojo.ValidationRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用于监听excel内部的数据，从而进行数据校验(调用easyexcel)
 * @author: chun
 **/

@Slf4j
public class ExcelListener extends AnalysisEventListener<ValidationRecord.Excel> {

    private List<ValidationRecord.Excel> data = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(ValidationRecord.Excel excel, AnalysisContext analysisContext) {
        log.info("读到数据  ",excel);
        data.add(excel);
    }

    //所有数据解析完后调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成");
    }
}
