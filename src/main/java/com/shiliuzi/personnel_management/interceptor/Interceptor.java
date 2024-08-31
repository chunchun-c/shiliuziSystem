package com.shiliuzi.personnel_management.interceptor;


import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.service.UserService;
import com.shiliuzi.personnel_management.utils.JwtUtil;
import com.shiliuzi.personnel_management.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        if (loginUser == null) {
            response.getWriter().print("please login first");
            return false;
        }

        //获取token
        String token = JwtUtil.getToken(request);
        //验证token
        User user = JwtUtil.getUserByToken(token);
        JwtUtil.testToken(user, token);
        //将用户信息和请求url存入上下文
        String URL = String.valueOf(request.getRequestURL());
        ThreadLocalUtil.setUrl(URL);
        ThreadLocalUtil.setUser(loginUser);
        return true;
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
        ThreadLocalUtil.clear();
    }

}
