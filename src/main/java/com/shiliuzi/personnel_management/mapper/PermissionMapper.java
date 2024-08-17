package com.shiliuzi.personnel_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiliuzi.personnel_management.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select permission_id from role_permission where role_id= #{roleId}")
    List<Integer> getPermissionIdByRoleId(Integer roleId);

    @Select("select permission_id from group_permission where group_id= #{groupId}")
    List<Integer> getPermissionIdByGroupId(Integer groupId);
}
