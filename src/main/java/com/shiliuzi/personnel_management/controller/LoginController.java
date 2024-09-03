package com.shiliuzi.personnel_management.controller;

import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;
import com.shiliuzi.personnel_management.service.RoleService;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.CheckCodeUtil;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import com.shiliuzi.personnel_management.utils.MD5Util;
import com.shiliuzi.personnel_management.utils.ReflectionUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/enter")
    public Result login(HttpSession session, @RequestBody User.LoginUser loginUser){


        // 使用 ReflectionUtils 将 loginUser 对象转换为 Map
        Map<String, Object> loginUserMap = ReflectionUtil.toMap(loginUser);

        // 从 Map 中获取字段值
        String password = (String) loginUserMap.get("password");
        String name = (String) loginUserMap.get("name");
        String captcha = (String) loginUserMap.get("captcha");

        String checkCode = (String) session.getAttribute("checkCode");
        //先判断验证码
        if (session.getAttribute("checkCode") == null || !checkCode.equalsIgnoreCase(captcha)) {
            return Result.fail("验证码错误");
        }

        //根据账号密码获取该用户
        Result loginResult = userService.getUserNameAndPassword(name, MD5Util.EncodeByMd5(name, password));

        //判断账号密码是否正确
        if (loginResult.isSuccess()){
            User user = (User)loginResult.getData();

            //将id和password返回生成token
            String jwt = JwtUtil.makeToken(user.getId().toString(), user.getPassword());
            //设置用户权限
            Result listByUserId = userService.getPermissionListByUserId(user.getId());
            List<Permission> list = (List<Permission>) listByUserId.getData();
            user.setPermissionList(list);
            //将该用户保存到session中
            session.setAttribute("loginUser",user);

            //获取用户的角色id
            String roleName = (String) roleService.getRoleByUserId(user.getId()).getData();
            String[] resultArray = { jwt, roleName};
            return Result.success("登录成功",resultArray);
        }else {
            return Result.fail(AppExceptionCodeMsg.USERID_PWD_WRONG);
        }

    }

    //生成验证码
    @GetMapping("/checkCode")
    public Result checkCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();

            String captcha = CheckCodeUtil.outputVerifyImage(100, 50, outputStream, 4);

            //放入session中，与后端生成的验证码进行对比
            HttpSession session = request.getSession();
            session.setAttribute("checkCode",captcha);

            return Result.success("传输验证码成功");
        } catch (IOException e) {
            return Result.fail("发送验证码失败");
        }
    }
}
