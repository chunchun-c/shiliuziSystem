package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.mapper.UserMapper;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.PermissionService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;

    @Autowired
    PermissionService permissionService;

    //获取用户权限列表
    @Override
    public Result getPermissionListByUserId(Integer userId) {
        //获取组别，角色id
        List<Integer> roleIdList = (List<Integer>) roleService.getRoleIdByUserId(userId).getData();
        List<Integer> groupIdList = (List<Integer>) groupService.getGroupIdByUserId(userId).getData();
        //查询权限
        List<Permission> permissionList = (List<Permission>) permissionService.getPermissionList(roleIdList,groupIdList);
        return Result.success(permissionList);
    }
}
