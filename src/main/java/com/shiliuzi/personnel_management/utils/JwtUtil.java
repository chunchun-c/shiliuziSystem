package com.shiliuzi.personnel_management.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil{
    private static UserService staticUserService;

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }

    //生成token
    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId)//userId为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))//2小时后token到期
                .sign(Algorithm.HMAC256(sign));//password为token密钥
    }

    //根据token查询用户
    private User getUserByToken(String token) {
        String userId;
        User loginUser;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            loginUser = staticUserService.getById(Integer.parseInt(userId));

        } catch (Exception e) {
            throw new AppException(AppExceptionCodeMsg.TOKEN_TEST_ERROR);
        }
        return loginUser;
    }

    //token验证
    private void testToken(User loginUser,String token){
        //用户密码加签验证token
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(loginUser.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AppException(AppExceptionCodeMsg.TOKEN_TEST_ERROR);
        }
    }

}