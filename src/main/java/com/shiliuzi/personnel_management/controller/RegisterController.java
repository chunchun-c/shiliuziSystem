package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;


    //注册接口
    @PostMapping("/register")
    public Result register(@RequestBody @Validated User.RegisterUser registerUser) {

        //检查用户名是否有重复
        Result userByNameRet = userService.getUserByName(registerUser.getName());
        if (userByNameRet.isSuccess()){
            return Result.fail("用户名已存在");
        }

        //判断groupId是否合法
        if (registerUser.getGroupId()>groupService.list().size()){
            return Result.fail("组别id错误");
        }

        //保存用户到数据库
        User user = new User(registerUser.getName(),registerUser.getStudentId(),registerUser.getGradeId(),
                MD5Util.EncodeByMd5(registerUser.getName(),registerUser.getPassword()));
        userService.save(user);
        //更新rbac信息
        Integer userId=user.getId();
        Result addUserRole = roleService.addUserRole(userId, registerUser.getRoleId());
        Result addUserGroup = groupService.addUserGroup(userId, registerUser.getGroupId());

        if (addUserRole.isSuccess() && addUserGroup.isSuccess()){
            return Result.success("注册成功",null);
        }else {
            return Result.fail("注册失败");
        }
    }


}
