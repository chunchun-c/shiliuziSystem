package com.shiliuzi.personnel_management.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.jwt.JWTUtil;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import com.shiliuzi.personnel_management.utils.MD5Util;
import com.shiliuzi.personnel_management.utils.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User.LoginUser loginUser){

        // 使用 ReflectionUtils 将 loginUser 对象转换为 Map
        Map<String, Object> loginUserMap = ReflectionUtil.toMap(loginUser);

        // 从 Map 中获取字段值
        String password = (String) loginUserMap.get("password");
        String name = (String) loginUserMap.get("name");
        String captcha = (String) loginUserMap.get("captcha");


        Result loginResult = userService.getUserNameAndPassword(name, MD5Util.EncodeByMd5(name, password), captcha);
        if (loginResult.isSuccess()){

            return Result.success("登录成功",JwtUtil.getToken());
        }else {
            return Result.fail("登录失败");
        }

        return null;
    }
}
