package com.shiliuzi.personnel_management;

import com.shiliuzi.personnel_management.utils.MD5Util;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @org.junit.jupiter.api.Test
    public void md5test() {
        String password = "123456";
        String name="张三";
        System.out.println(new MD5Util().EncodeByMd5(name,password));
    }
}
