package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.Role;
import com.shiliuzi.personnel_management.result.Result;

public interface RoleService extends IService<Role> {

    Result getRoleIdByUserId(Integer UserId);

    Result addUserRole(Integer UserId, Integer RoleId);

    Result getRoleByUserId(Integer userId);
}
