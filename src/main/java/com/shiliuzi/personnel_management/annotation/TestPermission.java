package com.shiliuzi.personnel_management.annotation;

import java.lang.annotation.*;

//需要检验权限的接口加上此注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestPermission {

}
