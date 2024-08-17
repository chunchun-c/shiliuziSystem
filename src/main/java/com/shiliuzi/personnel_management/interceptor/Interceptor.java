package com.shiliuzi.personnel_management.interceptor;

import com.shiliuzi.personnel_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    
}
