package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.RoleMapper;
import com.shiliuzi.personnel_management.mapper.UserRoleMapper;
import com.shiliuzi.personnel_management.pojo.Role;
import com.shiliuzi.personnel_management.pojo.UserRole;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public Result getRoleIdByUserId(Integer UserId) {
        List<Integer> roleIdList = roleMapper.getRoleIdByUserId(UserId);
        if (!roleIdList.isEmpty()){
            return Result.success(roleIdList);
        }else {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
    }

    @Override
    public Result addUserRole(Integer UserId, Integer RoleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(UserId);
        userRole.setRoleId(RoleId);
        int insert = userRoleMapper.insert(userRole);
        if (insert!=1){
            return Result.fail("数据插入失败");
        }else {
            return Result.success();
        }
    }

    //获取用户角色
    @Override
    public Result getRoleByUserId(Integer userId) {
        //查询角色id
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.select("role_id").eq("user_id", userId);
        UserRole userRole = userRoleMapper.selectOne(userRoleWrapper);
        //查询角色信息
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("id", userRole.getRoleId());
        Role role = roleMapper.selectOne(roleWrapper);
        if (role!=null){
            return Result.success(role);
        }else {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
    }
}
