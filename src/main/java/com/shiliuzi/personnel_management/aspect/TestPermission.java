package com.shiliuzi.personnel_management.aspect;

import cn.hutool.core.util.StrUtil;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.interceptor.PublicPaths;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import com.shiliuzi.personnel_management.utils.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestPermission {

    @Pointcut("@annotation(com.shiliuzi.personnel_management.annotation.TestPermission)")
    public void testPointCut(){}

    @Around("testPointCut()")
    public void around (ProceedingJoinPoint joinPoint) throws Throwable {
        //权限检查
        testPermission(ThreadLocalUtil.getUrl(),ThreadLocalUtil.getUser());
    }

    //检查权限
    private void testPermission(String URL, User user){
        String reqURL = "/" + StrUtil.subAfter(URL, "/", true);
        //检查是否为公共请求
        if (isPublicPath(reqURL)) {
            return;
        }
        //检查该用户是否有权限访问
        boolean flag = false;
        for (Permission permission : user.getPermissionList()) {
            if (URL.contains(permission.getPath())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new AppException(AppExceptionCodeMsg.PERMISSION_ERROR);
        }
    }

    //检查是否为公共路径
    private boolean isPublicPath(String requestUri) {
        for (PublicPaths path : PublicPaths.values()) {
            if (path.getPath().equals(requestUri)) {
                return true;
            }
        }
        return false;
    }
}
