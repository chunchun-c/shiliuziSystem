package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.mapper.RPRecordMapper;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import com.shiliuzi.personnel_management.service.RPRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RPRecordServiceImpl extends ServiceImpl<RPRecordMapper, RPRecords> implements RPRecordService {

    @Autowired
    RPRecordMapper rpRecordMapper;


}
