package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;

    //注册接口
    @PostMapping("/register")
    public Result register(String name, String studentId, Integer gradeId, String password, Integer groupId, Integer roleId) {
        //格式校验
        if (name.length()<1 || name.length()>10){
            return Result.fail("姓名格式错误");
        }
        if (!Pattern.matches("^\\d{10}$", studentId)){
            return Result.fail("学号格式错误");
        }
        if (gradeId<1 || gradeId>4){
            return Result.fail("年级信息错误");
        }
        if (!Pattern.matches("^(?![\\d_]+$)(?![a-z_]+$)(?![A-Z_]+$)[\\w\\d]{6,16}$",password)){
            return Result.fail("密码格式错误");
        }
        //保存用户到数据库
        User user = new User(name,studentId,gradeId,password);
        userService.save(user);
        //更新rbac信息
        Integer userId=user.getId();
        Result addUserRole = roleService.addUserRole(userId, roleId);
        Result addUserGroup = groupService.addUserGroup(userId, groupId);

        if (addUserRole.isSuccess() && addUserGroup.isSuccess()){
            return Result.success("注册成功",null);
        }else {
            return Result.fail("注册失败");
        }
    }

}
