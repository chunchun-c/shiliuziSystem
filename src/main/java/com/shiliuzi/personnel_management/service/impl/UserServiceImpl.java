package com.shiliuzi.personnel_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.mapper.UserMapper;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.PermissionService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Permission> permissionList = (List<Permission>) permissionService.getPermissionList(roleIdList,groupIdList).getData();
        return Result.success(permissionList);
    }

    //获取所有用户信息
    @Override
    public Result getAllUserInfo() {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.select("id","name","student_id","grade");
        List<User> users = userMapper.selectList(userWrapper);
        //重新包装为vo类
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : users) {
            UserVo userVo = new UserVo();
            Result roleRet = roleService.getRoleByUserId(user.getId());
            if (roleRet.isSuccess()) {
                userVo.setRole((String) roleRet.getData());
            }
            Result groupRet = groupService.getGroupByUserId(user.getId());
            if (groupRet.isSuccess()) {
                userVo.setGroup((String) groupRet.getData());
            }
            userVo.setName(user.getName());
            userVo.setStudentId(user.getStudentId());
            userVo.setGrade(user.getGrade());
            userVoList.add(userVo);
        }
        return Result.success(userVoList);
    }

    @Override
    public Result getUserNameAndPassword(String name,String password,String captcha) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .eq("password", password);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return Result.success();
        }else {
            return Result.fail(AppExceptionCodeMsg.ACCOUNT_ERROR);
        }
    }


}
