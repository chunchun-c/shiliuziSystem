package com.shiliuzi.personnel_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.personnel_management.pojo.RPRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RPRecordMapper extends BaseMapper<RPRecords> {
    //批量导入接口
    void batchInsert(List<RPRecords> list);
}
