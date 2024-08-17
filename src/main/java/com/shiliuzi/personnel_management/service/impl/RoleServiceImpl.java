package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.RoleMapper;
import com.shiliuzi.personnel_management.pojo.Role;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Result getRoleIdByUserId(Integer UserId) {
        List<Integer> RoleIdList = roleMapper.getRoleIdByUserId(UserId);
        if (!RoleIdList.isEmpty()){
            return Result.success(RoleIdList);
        }else {
            return Result.fail(AppExceptionCodeMsg.NO_FIT_DATA);
        }
    }
}
