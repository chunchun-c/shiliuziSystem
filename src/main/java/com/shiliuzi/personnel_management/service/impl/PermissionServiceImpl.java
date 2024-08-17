package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.PermissionMapper;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Result getPermissionIdByRoleId(Integer roleId) {
        List<Integer> permissionIdList = permissionMapper.getPermissionIdByRoleId(roleId);
        //由于有可能该用户除公共权限外没有任何权限，所以不做非空判断
        return Result.success(permissionIdList);
    }

    @Override
    public Result getPermissionIdByGroupId(Integer groupId) {
        List<Integer> permissionIdList = permissionMapper.getPermissionIdByGroupId(groupId);
        return Result.success(permissionIdList);
    }


    @Override
    public Result getPermissionList(List<Integer> roleIdList,List<Integer> groupIdList) {
        Set<Integer> permissionIdSet=new HashSet<>();

        for (Integer i : roleIdList) {
            try {
                permissionIdSet.addAll( (List<Integer>) getPermissionIdByRoleId(i).getData());
            } catch (Exception e) {
                throw new AppException(AppExceptionCodeMsg.NO_FIT_DATA);
            }
        }

        for (Integer i : groupIdList) {
            try {
                permissionIdSet.addAll( (List<Integer>) getPermissionIdByGroupId(i).getData());
            } catch (Exception e) {
                throw new AppException(AppExceptionCodeMsg.NO_FIT_DATA);
            }
        }

        List<Permission> permissionList=new ArrayList<>();

        for (Integer i : permissionIdSet) {
            permissionList.add(permissionMapper.selectById(i));
        }
        return Result.success(permissionList);
    }
}
