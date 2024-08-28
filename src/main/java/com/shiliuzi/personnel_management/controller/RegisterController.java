package com.shiliuzi.personnel_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.GroupService;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.JsonUtil;
import com.shiliuzi.personnel_management.utils.MD5Util;
import com.shiliuzi.personnel_management.utils.ReflectionUtil;
import com.shiliuzi.personnel_management.validate.group.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    public Result register(@RequestBody @Validated(RegisterModel.class) User.RegisterUser registerUser) {
        //获取数据

        // 使用工具类提取字段
        Map<String, Object> registerMap = ReflectionUtil.toMap(registerUser);

        // 从 Map 中获取字段值
        String name = (String) registerMap.get("name");
        String studentId = (String) registerMap.get("studentId");
        Integer gradeId = (Integer) registerMap.get("gradeId");
        String password = (String) registerMap.get("password");
        Integer groupId = (Integer) registerMap.get("groupId");
        Integer roleId = (Integer) registerMap.get("roleId");

        //格式校验
        if (name==null || name.length()<1 || name.length()>10){
            throw new AppException(AppExceptionCodeMsg.INVALID_NAME_FORMAT);
            //return Result.fail("姓名格式错误");
        }
        if (!Pattern.matches("^\\d{10}$", studentId)){
            throw new AppException(AppExceptionCodeMsg.INVALID_STUDENT_ID_FORMAT);
            //return Result.fail("学号格式错误");
        }
        if (gradeId==null || gradeId<1 || gradeId>4){
            throw new AppException(AppExceptionCodeMsg.GRADE_ERROR);
            //return Result.fail("年级信息错误");
        }
        if (!Pattern.matches("^(?![\\d_]+$)(?![a-z_]+$)(?![A-Z_]+$)[\\w\\d]{6,16}$",password)){
            throw new AppException(AppExceptionCodeMsg.INVALID_PASSWORD_FORMAT);
            //return Result.fail("密码格式错误");
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
