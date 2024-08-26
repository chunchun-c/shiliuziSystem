package com.shiliuzi.personnel_management.interceptor;

import cn.hutool.core.util.StrUtil;
import com.shiliuzi.personnel_management.exception.AppException;
import com.shiliuzi.personnel_management.exception.AppExceptionCodeMsg;
import com.shiliuzi.personnel_management.pojo.Permission;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



@Component
public class Interceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查是否登录
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser==null){
            //转发到登录页
            request.setAttribute("msg", "请先登录");
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }

        //获取token
        String token= JwtUtil.getToken(request);
        //验证token
        User user=JwtUtil.getUserByToken(token);
        JwtUtil.testToken(user,token);
        //验证权限
        String URL = String.valueOf(request.getRequestURL());
        testPermission(URL,loginUser);
        return true;
    }

    //检查权限
    private void testPermission(String URL,User user){
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

    //目标方法执行之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //页面渲染之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
