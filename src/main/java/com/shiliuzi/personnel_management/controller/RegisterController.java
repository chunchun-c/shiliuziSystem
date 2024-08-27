package com.shiliuzi.personnel_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.JsonUtil;
import com.shiliuzi.personnel_management.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

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
    public Result register(@RequestBody String userForm) {
        //获取数据
        JsonNode jsonNode = JsonUtil.jsonToJsonNode(userForm);
        String name=jsonNode.get("name").asText();
        String studentId=jsonNode.get("studentId").asText();
        Integer gradeId=jsonNode.get("gradeId").asInt();
        String password=jsonNode.get("password").asText();
        Integer groupId=jsonNode.get("groupId").asInt();
        Integer roleId=jsonNode.get("roleId").asInt();

        //格式校验
        if (name==null || name.length()<1 || name.length()>10){
            return Result.fail("姓名格式错误");
        }
        if (!Pattern.matches("^\\d{10}$", studentId)){
            return Result.fail("学号格式错误");
        }
        if (gradeId==null || gradeId<1 || gradeId>4){
            return Result.fail("年级信息错误");
        }
        if (!Pattern.matches("^(?![\\d_]+$)(?![a-z_]+$)(?![A-Z_]+$)[\\w\\d]{6,16}$",password)){
            return Result.fail("密码格式错误");
        }
        //保存用户到数据库
        User user = new User(name,studentId,gradeId, MD5Util.EncodeByMd5(name,password));
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
