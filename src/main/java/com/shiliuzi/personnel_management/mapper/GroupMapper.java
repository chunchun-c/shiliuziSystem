package com.shiliuzi.personnel_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.personnel_management.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    @Select(" select group_id from user_group where user_id= #{userId}")
    List<Integer> getGroupIdByUserId(Integer userId);
}
