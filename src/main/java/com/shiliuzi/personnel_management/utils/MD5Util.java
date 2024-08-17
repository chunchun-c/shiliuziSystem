package com.shiliuzi.personnel_management.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * md5加密工具
 */

@Component
public class MD5Util {
    public String EncodeByMd5(String salt, String password) {
        String saltPassword=salt+"/"+password;
        String md5Password = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
        return md5Password;
    }
}
