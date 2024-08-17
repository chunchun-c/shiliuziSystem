package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.result.Result;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    Result getPermissionIdByRoleId(Integer roleId);

    Result getPermissionIdByGroupId(Integer groupId);

    Result getPermissionList(List<Integer> roleIdList, List<Integer> groupIdList);
}
