package com.shiliuzi.personnel_management.config;

import com.shiliuzi.personnel_management.interceptor.Interceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Resource
    private Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).
                addPathPatterns("/**").
                excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**","/js/**");
    }
}
