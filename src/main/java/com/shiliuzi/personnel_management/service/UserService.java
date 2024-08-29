package com.shiliuzi.personnel_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiliuzi.personnel_management.pojo.User;
import com.shiliuzi.personnel_management.result.Result;

public interface UserService extends IService<User> {

    Result getPermissionListByUserId(Integer userId);

    Result getAllUserInfo();

    Result getUserNameAndPassword(String name,String password,String captcha);

    Result getUserByName(String username);
}
