package com.shiliuzi.personnel_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.personnel_management.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
