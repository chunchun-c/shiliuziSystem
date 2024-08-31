package com.shiliuzi.personnel_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.personnel_management.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select(" select role_id from user_role where user_id= #{userId}")
    List<Integer> getRoleIdByUserId(Integer userId);
}
